#!/bin/bash

###############################################################################
# 目标管理接口测试脚本
# 
# 功能：测试所有目标管理相关的 API 接口
# 使用方法：./test-goal.sh
###############################################################################

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# 测试环境配置
BASE_URL="${BASE_URL:-http://localhost:8084}"
TOKEN="${TOKEN:-}"

# 测试统计
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

# 工具函数
print_title() {
    echo -e "\n${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}\n"
}

print_test() {
    echo -e "${YELLOW}[TEST] $1${NC}"
}

print_success() {
    echo -e "${GREEN}[PASS] $1${NC}"
    ((PASSED_TESTS++))
}

print_fail() {
    echo -e "${RED}[FAIL] $1${NC}"
    echo -e "${RED}       $2${NC}"
    ((FAILED_TESTS++))
}

http_request() {
    local method=$1
    local url=$2
    local data=$3
    local headers=""
    
    if [ -n "$TOKEN" ]; then
        headers="-H 'Authorization: Bearer $TOKEN'"
    fi
    
    if [ "$method" = "GET" ] || [ "$method" = "DELETE" ]; then
        eval curl -s -X $method "$BASE_URL$url" $headers
    else
        eval curl -s -X $method "$BASE_URL$url" \
            -H "'Content-Type: application/json'" \
            $headers \
            -d "'$data'"
    fi
}

check_response() {
    local response=$1
    local expected_code=$2
    local test_name=$3
    
    ((TOTAL_TESTS++))
    
    local code=$(echo $response | jq -r '.code // .status // 0')
    
    if [ "$code" = "$expected_code" ]; then
        print_success "$test_name"
        return 0
    else
        print_fail "$test_name" "Expected: $expected_code, Got: $code"
        return 1
    fi
}

###############################################################################
# 开始测试
###############################################################################

print_title "目标管理接口测试"

# 测试1: 分页查询目标列表
print_test "1. 分页查询目标列表"
response=$(http_request "GET" "/goal/pageQuery?pageNum=1&pageSize=10")
check_response "$response" "200" "分页查询目标列表"

# 测试2: 按状态筛选
print_test "2. 按状态筛选目标"
response=$(http_request "GET" "/goal/pageQuery?status=IN_PROGRESS")
check_response "$response" "200" "按状态筛选目标"

# 测试3: 按优先级筛选
print_test "3. 按优先级筛选目标"
response=$(http_request "GET" "/goal/pageQuery?priority=HIGH")
check_response "$response" "200" "按优先级筛选目标"

# 测试4: 新增目标
print_test "4. 新增目标"
goal_data='{
  "title": "自动化测试目标-'$(date +%s)'",
  "description": "这是一个通过脚本创建的测试目标",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-03-31 23:59:59",
  "tags": "测试,自动化"
}'
response=$(http_request "POST" "/goal" "$goal_data")
check_response "$response" "200" "新增目标"

# 提取目标ID
NEW_GOAL_ID=$(echo $response | jq -r '.data // .goalId // 0')

if [ "$NEW_GOAL_ID" != "0" ] && [ "$NEW_GOAL_ID" != "null" ]; then
    echo -e "${GREEN}  ✓ 新创建的目标ID: $NEW_GOAL_ID${NC}"
    
    # 测试5: 查询目标详情
    print_test "5. 查询目标详情"
    response=$(http_request "GET" "/goal/$NEW_GOAL_ID")
    check_response "$response" "200" "查询目标详情"
    
    # 测试6: 编辑目标
    print_test "6. 编辑目标"
    update_data='{
      "goalId": '$NEW_GOAL_ID',
      "title": "更新后的测试目标",
      "description": "描述已更新",
      "priority": "MEDIUM",
      "tags": "测试,更新"
    }'
    response=$(http_request "PUT" "/goal" "$update_data")
    check_response "$response" "200" "编辑目标"
    
    # 测试7: 开始目标
    print_test "7. 开始目标"
    response=$(http_request "POST" "/goal/start/$NEW_GOAL_ID")
    check_response "$response" "200" "开始目标"
    
    # 测试8: 再次查询目标详情（验证状态变化）
    print_test "8. 验证目标状态变化"
    response=$(http_request "GET" "/goal/$NEW_GOAL_ID")
    status=$(echo $response | jq -r '.data.status // ""')
    if [ "$status" = "IN_PROGRESS" ]; then
        print_success "目标状态已变为 IN_PROGRESS"
        ((TOTAL_TESTS++))
        ((PASSED_TESTS++))
    else
        print_fail "目标状态验证失败" "Expected: IN_PROGRESS, Got: $status"
        ((TOTAL_TESTS++))
        ((FAILED_TESTS++))
    fi
    
    # 测试9: 删除目标
    print_test "9. 删除目标"
    response=$(http_request "DELETE" "/goal/$NEW_GOAL_ID")
    check_response "$response" "200" "删除目标"
    
    # 测试10: 验证目标已删除
    print_test "10. 验证目标已删除"
    response=$(http_request "GET" "/goal/$NEW_GOAL_ID")
    code=$(echo $response | jq -r '.code // .status // 0')
    if [ "$code" != "200" ]; then
        print_success "目标已成功删除"
        ((TOTAL_TESTS++))
        ((PASSED_TESTS++))
    else
        print_fail "目标删除验证失败" "目标仍然存在"
        ((TOTAL_TESTS++))
        ((FAILED_TESTS++))
    fi
else
    echo -e "${RED}  ✗ 无法获取新创建的目标ID，跳过后续测试${NC}"
fi

# 测试11: 查询不存在的目标
print_test "11. 查询不存在的目标"
response=$(http_request "GET" "/goal/999999")
code=$(echo $response | jq -r '.code // .status // 0')
if [ "$code" != "200" ]; then
    print_success "正确处理不存在的目标"
    ((TOTAL_TESTS++))
    ((PASSED_TESTS++))
else
    print_fail "错误处理不存在的目标" "应该返回错误"
    ((TOTAL_TESTS++))
    ((FAILED_TESTS++))
fi

# 测试12: 缺少必填字段
print_test "12. 测试参数校验（缺少title）"
invalid_data='{
  "description": "缺少标题的目标",
  "priority": "HIGH"
}'
response=$(http_request "POST" "/goal" "$invalid_data")
code=$(echo $response | jq -r '.code // .status // 0')
if [ "$code" != "200" ]; then
    print_success "参数校验正常"
    ((TOTAL_TESTS++))
    ((PASSED_TESTS++))
else
    print_fail "参数校验失败" "应该拒绝缺少必填字段的请求"
    ((TOTAL_TESTS++))
    ((FAILED_TESTS++))
fi

###############################################################################
# 测试结果汇总
###############################################################################

print_title "测试结果汇总"

echo "总测试数: $TOTAL_TESTS"
echo "通过数: $PASSED_TESTS"
echo "失败数: $FAILED_TESTS"

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "\n${GREEN}✓ 所有目标管理接口测试通过！${NC}\n"
    exit 0
else
    echo -e "\n${RED}✗ 有 $FAILED_TESTS 个测试失败${NC}\n"
    exit 1
fi
