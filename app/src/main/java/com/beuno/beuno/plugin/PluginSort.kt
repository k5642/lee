package com.beuno.beuno.plugin

import com.beuno.beuno.shortcut.logger

/**
 * 排序算法
 */
object PluginSort {
    val NUMS = arrayOf(43, 19, 33, 20, 48, 41, 31, 28, 17, 35, 46, 5, 17, 26, 45, 50, 8, 17)

    fun test() {

        val nums = NUMS.copyOf()
        nums.also {
            quick(it)
        }
        nums.forEach {
            it.logger()
        }
    }

    /** 冒泡排序
        do
            swapped = false
            for i = 1 to indexOfLastUnsortedElement-1
                if leftElement > rightElement
                    swap(leftElement, rightElement)
                    swapped = true
        while swapped
     */
    fun bubble(nums: Array<Int>) {
        var swapped: Boolean
        var indexOfLastUnsortedElement = nums.size
        do {
            swapped = false
            indexOfLastUnsortedElement--
            for (idx in 1.. indexOfLastUnsortedElement) {
                val preIdx = idx - 1
                if (nums[preIdx] > nums[idx]) {
                    swap(nums, preIdx, idx)
                    swapped = true
                }
            }
        } while (swapped)
    }

    /** 选择排序
        repeat (numOfElements - 1) times
            set the first unsorted element as the minimum
            for each of the unsorted elements
                if element < currentMinimum
                    set element as new minimum
            swap minimum with first unsorted position
     */
    fun selection(nums: Array<Int>) {
        var minIdx: Int
        var minValue: Int
        for (idx in 0 until nums.size) {
            minIdx = idx
            minValue = nums[idx]
            for (comparedIdx in idx + 1 until nums.size) {
                if (nums[comparedIdx] < minValue) {
                    minIdx = comparedIdx
                    minValue = nums[comparedIdx]
                }
            }
            if (idx != minIdx)
                swap(nums, idx, minIdx)
        }
    }

    /** 插值排序
        mark first element as sorted
        for each unsorted element X
            'extract' the element X
            for j = lastSortedIndex down to 0
                if current element j > X
                    move sorted element to the right by 1
                break loop and insert X here
     */
    fun insert(nums: ArrayList<Int>) {
//        for (curIdx in 1 until nums.size) {
//            for (sortedIdx in curIdx downTo 0)
//        }

    }

    fun mer(nums: Array<Int>) {

    }

    /**
     * 快排
        for each (unsorted) partition
        set first element as pivot
            storeIndex = pivotIndex + 1
            for i = pivotIndex + 1 to rightmostIndex
                if element[i] < element[pivot]
                    swap(i, storeIndex); storeIndex++
            swap(pivot, storeIndex - 1)
     */
    fun quick(nums: Array<Int>) {
        fun realSort(startIdx: Int, endIdx: Int) {
            if (startIdx >= endIdx) return
            val pivot = nums[startIdx]
            var edgeIdx = startIdx + 1
            for (curIdx in (startIdx + 1)..endIdx) {
                if (nums[curIdx] < pivot) {
                    swap(nums, curIdx, edgeIdx)
                    edgeIdx++
                }
            }
            edgeIdx--
            swap(nums, startIdx, edgeIdx)
            realSort(startIdx, edgeIdx - 1)
            realSort(edgeIdx + 1, endIdx)
        }
        realSort(0, nums.size - 1)
    }

    fun rq(nums: Array<Int>) {

    }

    fun cou(nums: Array<Int>) {

    }

    fun rad(nums: Array<Int>) {

    }


    // --------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------

    private fun swap(nums: Array<Int>, idx1: Int, idx2: Int) {
        if (idx1 == idx2) return
        val tmp = nums[idx1]
        nums[idx1] = nums[idx2]
        nums[idx2] = tmp
    }
}