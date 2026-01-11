#!/bin/bash

###############################################################################
# 任务管理接口测试脚本
# 
# 功能：测试所有任务管理相关的 API 接口
# 使用方法：./test-task.sh
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
GOAL_ID="${GOAL_ID:-1}"

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

print_title "任务管理接口测试"

echo "测试配置："
echo "  - 服务地址: $BASE_URL"
echo "  - 目标ID: $GOAL_ID"
echo ""

# 测试1: 分页查询任务列表
print_test "1. 分页查询任务列表"
response=$(http_request "GET" "/task/pageQuery?pageNum=1&pageSize=10")
check_response "$response" "200" "分页查询任务列表"

# 测试2: 按目标ID筛选
print_test "2. 按目标ID筛选任务"
response=$(http_request "GET" "/task/pageQuery?goalId=$GOAL_ID")
check_response "$response" "200" "按目标ID筛选任务"

# 测试3: 按状态筛选
print_test "3. 按状态筛选任务"
response=$(http_request "GET" "/task/pageQuery?status=COMPLETED")
check_response "$response" "200" "按状态筛选任务"

# 测试4: 查询目标下的任务
print_test "4. 查询目标下的任务"
response=$(http_request "GET" "/task/goal/$GOAL_ID")
check_response "$response" "200" "查询目标下的任务"

# 测试5: 新增任务
print_test "5. 新增任务"
task_data='{
  "goalId": '$GOAL_ID',
  "title": "自动化测试任务-'$(date +%s)'",
  "description": "这是一个通过脚本创建的测试任务",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-01-15 23:59:59",
  "sortOrder": 999
}'
response=$(http_request "POST" "/task" "$task_data")
check_response "$response" "200" "新增任务"

# 提取任务ID
NEW_TASK_ID=$(echo $response | jq -r '.data // .taskId // 0')

if [ "$NEW_TASK_ID" != "0" ] && [ "$NEW_TASK_ID" != "null" ]; then
    echo -e "${GREEN}  ✓ 新创建的任务ID: $NEW_TASK_ID${NC}"
    
    # 测试6: 查询任务详情
    print_test "6. 查询任务详情"
    response=$(http_request "GET" "/task/$NEW_TASK_ID")
    check_response "$response" "200" "查询任务详情"
    
    # 测试7: 编辑任务
    print_test "7. 编辑任务"
    update_data='{
      "taskId": '$NEW_TASK_ID',
      "title": "更新后的测试任务",
      "description": "描述已更新",
      "priority": "MEDIUM"
    }'
    response=$(http_request "PUT" "/task" "$update_data")
    check_response "$response" "200" "编辑任务"
    
    # 测试8: 开始任务
    print_test "8. 开始任务"
    response=$(http_request "POST" "/task/start/$NEW_TASK_ID")
    check_response "$response" "200" "开始任务"
    
    # 测试9: 验证任务状态变化
    print_test "9. 验证任务状态变化"
    response=$(http_request "GET" "/task/$NEW_TASK_ID")
    status=$(echo $response | jq -r '.data.status // ""')
    if [ "$status" = "IN_PROGRESS" ]; then
        print_success "任务状态已变为 IN_PROGRESS"
        ((TOTAL_TESTS++))
        ((PASSED_TESTS++))
    else
        print_fail "任务状态验证失败" "Expected: IN_PROGRESS, Got: $status"
        ((TOTAL_TESTS++))
        ((FAILED_TESTS++))
    fi
    
    # 测试10: 完成任务
    print_test "10. 完成任务"
    response=$(http_request "POST" "/task/complete/$NEW_TASK_ID?score=EXCELLENT&summary=测试任务完成")
    check_response "$response" "200" "完成任务"
    
    # 测试11: 验证任务完成状态
    print_test "11. 验证任务完成状态"
    response=$(http_request "GET" "/task/$NEW_TASK_ID")
    status=$(echo $response | jq -r '.data.status // ""')
    score=$(echo $response | jq -r '.data.score // ""')
    if [ "$status" = "COMPLETED" ] && [ "$score" = "EXCELLENT" ]; then
        print_success "任务已完成且评分正确"
        ((TOTAL_TESTS++))
        ((PASSED_TESTS++))
    else
        print_fail "任务完成状态验证失败" "Status: $status, Score: $score"
        ((TOTAL_TESTS++))
        ((FAILED_TESTS++))
    fi
    
    # 测试12: 删除任务
    print_test "12. 删除任务"
    response=$(http_request "DELETE" "/task/$NEW_TASK_ID")
    check_response "$response" "200" "删除任务"
    
    # 测试13: 验证任务已删除
    print_test "13. 验证任务已删除"
    response=$(http_request "GET" "/task/$NEW_TASK_ID")
    code=$(echo $response | jq -r '.code // .status // 0')
    if [ "$code" != "200" ]; then
        print_success "任务已成功删除"
        ((TOTAL_TESTS++))
        ((PASSED_TESTS++))
    else
        print_fail "任务删除验证失败" "任务仍然存在"
        ((TOTAL_TESTS++))
        ((FAILED_TESTS++))
    fi
else
    echo -e "${RED}  ✗ 无法获取新创建的任务ID，跳过后续测试${NC}"
fi

# 测试14: 查询不存在的任务
print_test "14. 查询不存在的任务"
response=$(http_request "GET" "/task/999999")
code=$(echo $response | jq -r '.code // .status // 0')
if [ "$code" != "200" ]; then
    print_success "正确处理不存在的任务"
    ((TOTAL_TESTS++))
    ((PASSED_TESTS++))
else
    print_fail "错误处理不存在的任务" "应该返回错误"
    ((TOTAL_TESTS++))
    ((FAILED_TESTS++))
fi

# 测试15: 缺少必填字段
print_test "15. 测试参数校验（缺少goalId）"
invalid_data='{
  "title": "缺少目标ID的任务",
  "priority": "HIGH"
}'
response=$(http_request "POST" "/task" "$invalid_data")
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

# 测试16: 测试标记未达成功能
print_test "16. 创建任务用于测试标记未达成"
task_data='{
  "goalId": '$GOAL_ID',
  "title": "测试未达成任务-'$(date +%s)'",
  "description": "用于测试标记未达成功能",
  "priority": "MEDIUM",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-01-15 23:59:59"
}'
response=$(http_request "POST" "/task" "$task_data")
TEST_TASK_ID=$(echo $response | jq -r '.data // .taskId // 0')

if [ "$TEST_TASK_ID" != "0" ] && [ "$TEST_TASK_ID" != "null" ]; then
    # 先开始任务
    http_request "POST" "/task/start/$TEST_TASK_ID" > /dev/null
    
    # 标记未达成
    print_test "17. 标记任务未达成"
    response=$(http_request "POST" "/task/notAchieved/$TEST_TASK_ID")
    check_response "$response" "200" "标记任务未达成"
    
    # 清理测试数据
    http_request "DELETE" "/task/$TEST_TASK_ID" > /dev/null
fi

###############################################################################
# 测试结果汇总
###############################################################################

print_title "测试结果汇总"

echo "总测试数: $TOTAL_TESTS"
echo "通过数: $PASSED_TESTS"
echo "失败数: $FAILED_TESTS"

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "\n${GREEN}✓ 所有任务管理接口测试通过！${NC}\n"
    exit 0
else
    echo -e "\n${RED}✗ 有 $FAILED_TESTS 个测试失败${NC}\n"
    exit 1
fi
