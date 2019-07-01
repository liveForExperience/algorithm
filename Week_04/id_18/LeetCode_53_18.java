package Week_04.id_18;

/**
 * @author LiveForExperience
 * @date 2019/6/30 16:35
 */
public class LeetCode_53_18 {
    public int maxSubArray(int[] nums) {
        int result = nums[0];
        int sum = 0;
        for (int num: nums) {
            if (sum >= 0) {
                sum += num;
            } else {
                sum = num;
            }
            result = Math.max(sum, result);
        }

        return result;
    }

    public int maxSubArray1(int[] nums) {
        int result = nums[0];
        int sum = nums[0];
        for (int num: nums) {
            sum = Math.max(num, sum + num);
            result = Math.max(result, sum);
        }

        return result;
    }

    public int maxSubArray2(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int result = dp[0];

        for (int i = 1; i < n; i++) {
            dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            result = Math.max(result, dp[i]);
        }

        return result;
    }
}
