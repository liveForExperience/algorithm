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
}
