#!/bin/bash

###############################################################################
# 统计分析接口测试脚本
# 
# 功能：测试所有统计分析相关的 API 接口
# 使用方法：./test-statistics.sh
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
    local headers=""
    
    if [ -n "$TOKEN" ]; then
        headers="-H 'Authorization: Bearer $TOKEN'"
    fi
    
    eval curl -s -X $method "$BASE_URL$url" $headers
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

check_field_exists() {
    local response=$1
    local field=$2
    local test_name=$3
    
    ((TOTAL_TESTS++))
    
    local value=$(echo $response | jq -r ".data.$field // null")
    
    if [ "$value" != "null" ]; then
        print_success "$test_name"
        return 0
    else
        print_fail "$test_name" "字段 $field 不存在"
        return 1
    fi
}

###############################################################################
# 开始测试
###############################################################################

print_title "统计分析接口测试"

# 测试1: 获取目标统计
print_test "1. 获取目标统计"
response=$(http_request "GET" "/statistics/goal")
check_response "$response" "200" "获取目标统计"

if [ $? -eq 0 ]; then
    # 验证统计数据字段
    print_test "2. 验证目标统计数据字段"
    
    check_field_exists "$response" "totalCount" "验证 totalCount 字段"
    check_field_exists "$response" "draftCount" "验证 draftCount 字段"
    check_field_exists "$response" "notStartedCount" "验证 notStartedCount 字段"
    check_field_exists "$response" "inProgressCount" "验证 inProgressCount 字段"
    check_field_exists "$response" "completedCount" "验证 completedCount 字段"
    check_field_exists "$response" "completionRate" "验证 completionRate 字段"
    
    # 打印统计数据
    echo -e "\n${BLUE}目标统计数据：${NC}"
    echo $response | jq '.data' 2>/dev/null || echo "无法解析JSON"
fi

# 测试3: 获取任务统计
print_test "3. 获取任务统计"
response=$(http_request "GET" "/statistics/task")
check_response "$response" "200" "获取任务统计"

if [ $? -eq 0 ]; then
    # 验证统计数据字段
    print_test "4. 验证任务统计数据字段"
    
    check_field_exists "$response" "totalCount" "验证 totalCount 字段"
    check_field_exists "$response" "notStartedCount" "验证 notStartedCount 字段"
    check_field_exists "$response" "inProgressCount" "验证 inProgressCount 字段"
    check_field_exists "$response" "completedCount" "验证 completedCount 字段"
    check_field_exists "$response" "completionRate" "验证 completionRate 字段"
    
    # 打印统计数据
    echo -e "\n${BLUE}任务统计数据：${NC}"
    echo $response | jq '.data' 2>/dev/null || echo "无法解析JSON"
fi

# 测试5: 获取数据看板
print_test "5. 获取数据看板"
response=$(http_request "GET" "/statistics/dashboard")
check_response "$response" "200" "获取数据看板"

if [ $? -eq 0 ]; then
    # 验证看板数据结构
    print_test "6. 验证数据看板结构"
    
    has_goal_stats=$(echo $response | jq -r '.data.goalStatistics // null')
    has_task_stats=$(echo $response | jq -r '.data.taskStatistics // null')
    
    ((TOTAL_TESTS++))
    if [ "$has_goal_stats" != "null" ] && [ "$has_task_stats" != "null" ]; then
        print_success "数据看板结构完整"
        ((PASSED_TESTS++))
    else
        print_fail "数据看板结构不完整" "缺少必要的统计数据"
        ((FAILED_TESTS++))
    fi
    
    # 打印看板数据
    echo -e "\n${BLUE}数据看板：${NC}"
    echo $response | jq '.data' 2>/dev/null || echo "无法解析JSON"
fi

# 测试7: 测试数据一致性
print_test "7. 测试统计数据一致性"

goal_response=$(http_request "GET" "/statistics/goal")
task_response=$(http_request "GET" "/statistics/task")
dashboard_response=$(http_request "GET" "/statistics/dashboard")

goal_total=$(echo $goal_response | jq -r '.data.totalCount // 0')
task_total=$(echo $task_response | jq -r '.data.totalCount // 0')
dashboard_goal_total=$(echo $dashboard_response | jq -r '.data.goalStatistics.totalCount // 0')
dashboard_task_total=$(echo $dashboard_response | jq -r '.data.taskStatistics.totalCount // 0')

((TOTAL_TESTS++))
if [ "$goal_total" = "$dashboard_goal_total" ] && [ "$task_total" = "$dashboard_task_total" ]; then
    print_success "统计数据一致性验证通过"
    ((PASSED_TESTS++))
    echo "  目标总数: $goal_total"
    echo "  任务总数: $task_total"
else
    print_fail "统计数据不一致" "Goal: $goal_total vs $dashboard_goal_total, Task: $task_total vs $dashboard_task_total"
    ((FAILED_TESTS++))
fi

# 测试8: 测试完成率计算
print_test "8. 验证完成率计算"

goal_completed=$(echo $goal_response | jq -r '.data.completedCount // 0')
goal_completion_rate=$(echo $goal_response | jq -r '.data.completionRate // 0')

if [ "$goal_total" != "0" ]; then
    expected_rate=$(echo "scale=2; $goal_completed * 100 / $goal_total" | bc)
    actual_rate=$(echo $goal_completion_rate | sed 's/%//')
    
    ((TOTAL_TESTS++))
    # 允许小数点误差
    diff=$(echo "$expected_rate - $actual_rate" | bc | sed 's/-//')
    if (( $(echo "$diff < 1" | bc -l) )); then
        print_success "目标完成率计算正确"
        ((PASSED_TESTS++))
        echo "  完成率: $goal_completion_rate"
    else
        print_fail "目标完成率计算错误" "Expected: ${expected_rate}%, Got: ${actual_rate}%"
        ((FAILED_TESTS++))
    fi
else
    echo "  跳过完成率验证（无目标数据）"
fi

###############################################################################
# 测试结果汇总
###############################################################################

print_title "测试结果汇总"

echo "总测试数: $TOTAL_TESTS"
echo "通过数: $PASSED_TESTS"
echo "失败数: $FAILED_TESTS"

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "\n${GREEN}✓ 所有统计分析接口测试通过！${NC}\n"
    exit 0
else
    echo -e "\n${RED}✗ 有 $FAILED_TESTS 个测试失败${NC}\n"
    exit 1
fi
