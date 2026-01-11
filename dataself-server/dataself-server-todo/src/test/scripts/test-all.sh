#!/bin/bash

###############################################################################
# OKR-Todo 系统完整测试脚本
# 
# 功能：测试所有 API 接口
# 使用方法：./test-all.sh
# 
# 注意：
# 1. 请先启动服务
# 2. 请先配置测试环境变量
# 3. 请先准备测试数据
###############################################################################

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 测试环境配置
BASE_URL="${BASE_URL:-http://localhost:8084}"
TOKEN="${TOKEN:-}"
USER_ID="${USER_ID:-1}"

# 测试统计
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

# 日志文件
LOG_FILE="test-results-$(date +%Y%m%d-%H%M%S).log"

###############################################################################
# 工具函数
###############################################################################

# 打印标题
print_title() {
    echo -e "\n${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}\n"
}

# 打印测试用例
print_test() {
    echo -e "${YELLOW}[TEST] $1${NC}"
}

# 打印成功
print_success() {
    echo -e "${GREEN}[PASS] $1${NC}"
    ((PASSED_TESTS++))
}

# 打印失败
print_fail() {
    echo -e "${RED}[FAIL] $1${NC}"
    echo -e "${RED}       $2${NC}"
    ((FAILED_TESTS++))
}

# 执行 HTTP 请求
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

# 检查响应状态
check_response() {
    local response=$1
    local expected_code=$2
    local test_name=$3
    
    ((TOTAL_TESTS++))
    
    local code=$(echo $response | jq -r '.code // .status // 0')
    
    if [ "$code" = "$expected_code" ]; then
        print_success "$test_name"
        echo "$test_name: PASS" >> $LOG_FILE
        return 0
    else
        print_fail "$test_name" "Expected code: $expected_code, Got: $code"
        echo "$test_name: FAIL (Expected: $expected_code, Got: $code)" >> $LOG_FILE
        echo "Response: $response" >> $LOG_FILE
        return 1
    fi
}

###############################################################################
# 测试前准备
###############################################################################

print_title "OKR-Todo 系统测试开始"

echo "测试配置："
echo "  - 服务地址: $BASE_URL"
echo "  - 用户ID: $USER_ID"
echo "  - 日志文件: $LOG_FILE"
echo ""

# 检查服务是否启动
print_test "检查服务状态"
response=$(curl -s "$BASE_URL/actuator/health" || echo '{"status":"DOWN"}')
status=$(echo $response | jq -r '.status // "DOWN"')

if [ "$status" != "UP" ]; then
    echo -e "${RED}错误: 服务未启动或无法访问${NC}"
    echo -e "${RED}请先启动服务: java -jar target/dataself-server-todo.jar${NC}"
    exit 1
fi

print_success "服务运行正常"

###############################################################################
# 目标管理接口测试
###############################################################################

print_title "1. 目标管理接口测试 (8个接口)"

# 1.1 分页查询目标列表
print_test "TC-G-001: 分页查询目标列表"
response=$(http_request "GET" "/goal/pageQuery?pageNum=1&pageSize=10")
check_response "$response" "200" "分页查询目标列表"

# 1.2 新增目标
print_test "TC-G-007: 新增目标"
goal_data='{
  "title": "测试目标-'$(date +%s)'",
  "description": "这是一个测试目标",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-03-31 23:59:59"
}'
response=$(http_request "POST" "/goal" "$goal_data")
check_response "$response" "200" "新增目标"

# 提取新创建的目标ID
NEW_GOAL_ID=$(echo $response | jq -r '.data // .goalId // 0')

if [ "$NEW_GOAL_ID" != "0" ] && [ "$NEW_GOAL_ID" != "null" ]; then
    echo "  新创建的目标ID: $NEW_GOAL_ID"
    
    # 1.3 查询目标详情
    print_test "TC-G-004: 查询目标详情"
    response=$(http_request "GET" "/goal/$NEW_GOAL_ID")
    check_response "$response" "200" "查询目标详情"
    
    # 1.4 编辑目标
    print_test "TC-G-010: 编辑目标"
    update_data='{
      "goalId": '$NEW_GOAL_ID',
      "title": "更新后的测试目标",
      "description": "这是更新后的描述",
      "priority": "MEDIUM"
    }'
    response=$(http_request "PUT" "/goal" "$update_data")
    check_response "$response" "200" "编辑目标"
    
    # 1.5 开始目标
    print_test "TC-G-016: 开始目标"
    response=$(http_request "POST" "/goal/start/$NEW_GOAL_ID")
    check_response "$response" "200" "开始目标"
    
    # 1.6 删除目标
    print_test "TC-G-013: 删除目标"
    response=$(http_request "DELETE" "/goal/$NEW_GOAL_ID")
    check_response "$response" "200" "删除目标"
fi

# 1.7 查询不存在的目标
print_test "TC-G-005: 查询不存在的目标"
response=$(http_request "GET" "/goal/999999")
code=$(echo $response | jq -r '.code // .status // 0')
if [ "$code" != "200" ]; then
    print_success "查询不存在的目标（正确返回错误）"
    ((TOTAL_TESTS++))
    ((PASSED_TESTS++))
else
    print_fail "查询不存在的目标" "应该返回错误，但返回了200"
    ((TOTAL_TESTS++))
    ((FAILED_TESTS++))
fi

###############################################################################
# 任务管理接口测试
###############################################################################

print_title "2. 任务管理接口测试 (9个接口)"

# 2.1 分页查询任务列表
print_test "TC-T-001: 分页查询任务列表"
response=$(http_request "GET" "/task/pageQuery?pageNum=1&pageSize=10")
check_response "$response" "200" "分页查询任务列表"

# 假设已有目标ID为1
EXISTING_GOAL_ID=1

# 2.2 新增任务
print_test "TC-T-010: 新增任务"
task_data='{
  "goalId": '$EXISTING_GOAL_ID',
  "title": "测试任务-'$(date +%s)'",
  "description": "这是一个测试任务",
  "priority": "HIGH",
  "expectedStartTime": "2026-01-01 00:00:00",
  "expectedEndTime": "2026-01-15 23:59:59"
}'
response=$(http_request "POST" "/task" "$task_data")
check_response "$response" "200" "新增任务"

# 提取新创建的任务ID
NEW_TASK_ID=$(echo $response | jq -r '.data // .taskId // 0')

if [ "$NEW_TASK_ID" != "0" ] && [ "$NEW_TASK_ID" != "null" ]; then
    echo "  新创建的任务ID: $NEW_TASK_ID"
    
    # 2.3 查询任务详情
    print_test "TC-T-004: 查询任务详情"
    response=$(http_request "GET" "/task/$NEW_TASK_ID")
    check_response "$response" "200" "查询任务详情"
    
    # 2.4 查询目标下的任务
    print_test "TC-T-007: 查询目标下的任务"
    response=$(http_request "GET" "/task/goal/$EXISTING_GOAL_ID")
    check_response "$response" "200" "查询目标下的任务"
    
    # 2.5 编辑任务
    print_test "TC-T-013: 编辑任务"
    update_data='{
      "taskId": '$NEW_TASK_ID',
      "title": "更新后的测试任务",
      "description": "这是更新后的描述",
      "priority": "MEDIUM"
    }'
    response=$(http_request "PUT" "/task" "$update_data")
    check_response "$response" "200" "编辑任务"
    
    # 2.6 开始任务
    print_test "TC-T-019: 开始任务"
    response=$(http_request "POST" "/task/start/$NEW_TASK_ID")
    check_response "$response" "200" "开始任务"
    
    # 2.7 完成任务
    print_test "TC-T-022: 完成任务"
    response=$(http_request "POST" "/task/complete/$NEW_TASK_ID?score=EXCELLENT&summary=测试完成")
    check_response "$response" "200" "完成任务"
    
    # 2.8 删除任务
    print_test "TC-T-016: 删除任务"
    response=$(http_request "DELETE" "/task/$NEW_TASK_ID")
    check_response "$response" "200" "删除任务"
fi

# 2.9 查询不存在的任务
print_test "TC-T-005: 查询不存在的任务"
response=$(http_request "GET" "/task/999999")
code=$(echo $response | jq -r '.code // .status // 0')
if [ "$code" != "200" ]; then
    print_success "查询不存在的任务（正确返回错误）"
    ((TOTAL_TESTS++))
    ((PASSED_TESTS++))
else
    print_fail "查询不存在的任务" "应该返回错误，但返回了200"
    ((TOTAL_TESTS++))
    ((FAILED_TESTS++))
fi

###############################################################################
# 统计分析接口测试
###############################################################################

print_title "3. 统计分析接口测试 (3个接口)"

# 3.1 获取目标统计
print_test "TC-S-001: 获取目标统计"
response=$(http_request "GET" "/statistics/goal")
check_response "$response" "200" "获取目标统计"

# 3.2 获取任务统计
print_test "TC-S-004: 获取任务统计"
response=$(http_request "GET" "/statistics/task")
check_response "$response" "200" "获取任务统计"

# 3.3 获取数据看板
print_test "TC-S-007: 获取数据看板"
response=$(http_request "GET" "/statistics/dashboard")
check_response "$response" "200" "获取数据看板"

###############################################################################
# 测试结果汇总
###############################################################################

print_title "测试结果汇总"

echo "总测试数: $TOTAL_TESTS"
echo "通过数: $PASSED_TESTS"
echo "失败数: $FAILED_TESTS"

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "\n${GREEN}✓ 所有测试通过！${NC}\n"
    exit 0
else
    echo -e "\n${RED}✗ 有 $FAILED_TESTS 个测试失败${NC}\n"
    echo "详细日志请查看: $LOG_FILE"
    exit 1
fi
