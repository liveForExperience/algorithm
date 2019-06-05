# 学习笔记
## LeetCode_26_18
### 题目：
给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

示例 1:
```
给定数组 nums = [1,1,2], 

函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。 

你不需要考虑数组中超出新长度后面的元素。
```
示例 2:
```
给定 nums = [0,0,1,1,1,2,2,3,3,4],

函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。

你不需要考虑数组中超出新长度后面的元素。
```
说明:
```
为什么返回数值是整数，但输出的答案是数组呢?

请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
```
你可以想象内部操作如下:
```
// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
int len = removeDuplicates(nums);

// 在函数里修改输入数组对于调用者是可见的。
// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
```
### 解法一
#### 思路
看到这道题的第一反应就是和覃超老师PPT_**Day-1-2中第24页Array实战的第1题_remove zero**的解题思路很相似。可以用两个游标来解这道题，一个负责遍历数组，一个负责做特殊的工作，remove zero是把非0数放入该游标指向的位置，而这题是把不重复的数放入该游标指向的位置。
#### 解题过程
1. 一开始使用了和remove zero很类似的代码，结果出现的数组越界的问题:
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int nonDuplicatesIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != nums[i + 1]) {
                nums[nonDuplicatesIndex++] = nums[i];
            }
        }
        
        return nonDuplicatesIndex;
    }
}
```
这时候的思路是，碰到第一个不重复的元素，我就把他放在第二个游标nonDuplicatesIndex指向的位置，然后让它向后移一位，同时第一个游标继续往后查。
但是这样就会导致遍历到最后一个元素的时候就会越界。

2. 于是就想，要么就比较游标i和它前一个元素，先解决越界的问题。同时，我也就要从下标1开始遍历，否则又得越界，那用人话来解释从1开始的话......又想了下，因为，数组第一个绝对就是不重复的呀，那放在nonDuplicatesIndex的位置天经地义啊，好了，逻辑自洽了。。。
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int nonDuplicatesIndex = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[nonDuplicatesIndex++] = nums[i];
            }
        }
        
        return nonDuplicatesIndex;
    }
}
```
这次的提交结果，耗时是3ms，只打败了50%多的人，说明时间还可以优化。

3. 于是就想到了用如下的方式试一下，耗时居然提升到了2ms。。。不知道是瞎猫碰到死耗子，还是本身就有精度的误差，其实没什么区别。。。
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int nonDuplicatesIndex = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue;
            }
            nums[nonDuplicatesIndex++] = nums[i];
        }
        
        return nonDuplicatesIndex;
    }
}
```
### 收获
当看完题目后，直接O(1)的从脑子里就闪出了remove zero的解题思路，当时感觉那叫一个爽啊。切身体会到五毒神掌，多做题真的很有用，让大脑形成了一种对某种题目的认知框架，可以辅助自己迅速找到解决问题的方案。
## LeetCode_242_18
### 题目
给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

示例 1:
```
输入: s = "anagram", t = "nagaram"
输出: true
```
示例 2:
```
输入: s = "rat", t = "car"
输出: false
```
说明:
```
你可以假设字符串只包含小写字母。
```
### 解法一
#### 思路
上来看到两个数比较，控制不住脾气，就是一顿**两个for的嵌套**。不过耗时1000ms+，有很大的优化空间。
### 解题过程
话不多说，就是干：
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        char[] tc = t.toCharArray();
        for (char sc: s.toCharArray()) {
            for (int i = 0; i < tc.length; i++) {
                if (sc == tc[i]) {
                    tc[i] = '#';
                    break;
                }
            }
        }
        
        for (char c: tc) {
            if (c != '#') {
                return false;
            }
        }
        
        return s.length() == t.length();
    }
}
```
因为有两个for嵌套，所以时间复杂度是O(n^2)，效率不高。
### 解法二
#### 思路
两个字符串的比较，其实也可以想成是组成的字符个数的比较，可以用一个map来统计两个字符串中各个字符的个数是否相等。
#### 解题过程
这个话也不多说了，继续干：
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        Map<String, Long> map = Arrays.stream(s.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (String ts: t.split("")) {
            if (map.containsKey(ts)) {
                map.put(ts, map.get(ts) - 1);
                continue;
            }
            return false;
        }
        
        for (Long value: map.values()) {
            if (value != 0) {
                return false;
            }
        }
        
        return true;    
    }
}
```
因为时间复杂度是O(n),所以时间确实是从一开始的1000ms缩短到了300ms不到，但是仍然只打败了5%的人,而且因为使用的是String，空间也多占用了20M。。。还可以优化。
### 解法三
#### 思路
先在解法二的基础上，优化下空间大小，那就使用char数组。
#### 解题过程
```java
public class Solution {
    public boolean isAnagram(String s, String t) {
        Map<Integer, Long> map = s.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (char c: t.toCharArray()) {
            int ci = (int)c;
            if (map.containsKey(ci)) {
                map.put(ci, map.get(ci) - 1);
                continue;
            }
            return false;
        }

        for (Long value: map.values()) {
            if (value != 0) {
                return false;
            }
        }

        return true;
    }
}
```
执行后，空间和解法一少了1M左右，同时速度比解法二也提升了，140ms了，但是还是只打败了5%，还可以优化。
### 解法四
#### 思路
在想出以上3种解法后，挣扎了10分多种，没再能想出更好的方法，于是使用五毒神掌第一式，看solution：
#### 解题过程
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        Arrays.sort(cs);
        Arrays.sort(ct);
        return Arrays.equals(cs, ct);
    }
}
```
看完代码，就想自己怎么这么蠢，现成的好东西不用。。。
### 解法五
#### 思路
继续找solution中的好解法：
#### 解题思路
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        int[] countArr = new int[26];
        
        for (int i = 0; i < cs.length; i++) {
            countArr[cs[i] - 'a']++;
            countArr[ct[i] - 'a']--;
        }
        
        for (int i: countArr) {
            if (i != 0) {
                return false;
            }
        }
        
        return ture;
    }
}
```
发现solution的第2个解法与我之前想的第2、3种很像，但它没有使用map来映射，而是直接使用arr[index] - 'a'的方法，巧妙的利用数组下标来映射字母并计数。提交后速度变得只有几ms了，提升的非常明显。这个方法的时间复杂是O(n)。
### 收获
通过5种解法的摸索和学习过程，有两点的收获：
- 感觉把题目提交并成功，而且每一次都比上一次更优化的时候，这种反馈确实会上瘾，现在感觉每次空下来，都想刷道题爽一下了。
- 在自己尝试了3种解法之后，开始搜寻和学习网上优质解法的时候，在看的时候，可以结合自己的思维过程，更好的体会到别人解法的思路，学习起来更有效率了，而且也更容易记忆。
## LeetCode_1047_18
### 题目
给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。

在 S 上反复执行重复项删除操作，直到无法继续删除。

在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。

示例：
```
输入："abbaca"
输出："ca"
解释：
例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
```
### 解法一
#### 思路
因为是比较相邻的两个字符是否相等，而且是在删去重复地内容后，继续不断比较地过程，所以非常像栈的push和pop的过程，所以想到了用栈来解决这道题(其实主要是题目分类在栈分类里：D)
#### 解题过程
1. 用一个栈来进行对字符数组遍历过程中压栈和出栈的过程；
2. 每次压栈之前都判断栈是否为空，或者栈顶字符是否与当前遍历元素相同；
3. 如果不同且非空，就压栈；
4. 否则就出栈，然后循环往复，直到遍历结束；
5. 之后再遍历一下这个栈，拼出字符串后得到结果。
```java
class Solution {
    public String removeDuplicates(String S) {
        if (S == null || "".equals(S)) {
            return S;
        }
        
        Stack<Character> stack = new Stack<>();
        for (char c: S.toCharArray()) {
            if (stack.isEmpty() || stack.peek() != c) {
                stack.push(c);
            } else {
                stack.pop();
            }
        }

        StringBuilder result = new StringBuilder();
        for (Character c: stack) {
            result.append(c);
        }

        return result.toString();
    }
}
```
提交结果后，耗时只打败了50%的人，可以继续优化。
### 解法二