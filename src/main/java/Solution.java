public class Solution {

    public static void main(String[] args) {
        int[] nums = {4, 1};
        System.out.println(search(nums, 1));
    }

    public static int search(int[] nums, int target) {
        int len = nums.length - 1;
        if(len == 0 && nums[0] != target) return -1;

        int mid = len / 2;
        int left = target > nums[len] ? 0 : mid;
        int right = left == 0 ? mid + 1 : len;

        while (left <= right) {
            mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

}