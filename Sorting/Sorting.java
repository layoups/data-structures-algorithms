import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Karim Layoun
 * @userid klayoun3
 * @GTID 903210227
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The comparator "
                    + "or array is null");
        }
        int i = 0;
        boolean swap = true;
        while (i < arr.length && swap) {
            swap = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swap = true;
                }
            }
            i++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The comparator "
                    + "or array is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
                j--;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The comparator "
                    + "or array is null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    /**
     * helper method for quicksort
     *
     * @param arr the array to be sorted
     * @param l the starting index of the subarray
     * @param r the ending index of the subarray
     * @param rand the random generator
     * @param comp the comparator
     * @param <T> data type to sort
     */
    private static <T> void quickSort(T[] arr, int l, int r, Random rand,
                               Comparator<T> comp) {
        if (l < r) {
            int pind = rand.nextInt(r - l) + l;
            T swap = arr[pind];
            arr[pind] = arr[l];
            arr[l] = swap;
            int lind = l + 1;
            int rind = r - 1;
            while (lind <= rind) {
                while (lind <= rind && comp.compare(arr[l], arr[lind]) >= 0) {
                    lind++;
                }
                while (lind <= rind && comp.compare(arr[l], arr[rind]) <= 0) {
                    rind--;
                }
                if (lind < rind) {
                    T swp = arr[lind];
                    arr[lind] = arr[rind];
                    arr[rind] = swp;
                    lind++;
                    rind--;
                }
            }
            T temp = arr[l];
            arr[l] = arr[rind];
            arr[rind] = temp;
            quickSort(arr, l, rind, rand, comp);
            quickSort(arr, rind + 1, r, rand, comp);
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The comparator "
                    + "or array is null");
        }
        quickSort(arr, 0, arr.length, rand, comparator);


    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The comparator "
                    + "or array is null");
        }
        if (arr.length > 1) {
            int mid = arr.length / 2;
            T[] left = (T[]) new Object[mid];
            T[] right = (T[]) new Object[arr.length - mid];
            for (int i = 0; i < mid; i++) {
                left[i] = arr[i];
            }
            for (int i = mid; i < arr.length; i++) {
                right[i - mid] = arr[i];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            int lind = 0;
            int rind = 0;
            int curr = 0;
            while (lind < mid && rind < arr.length - mid) {
                if (comparator.compare(left[lind], right[rind]) <= 0) {
                    arr[curr] = left[lind];
                    lind++;
                } else {
                    arr[curr] = right[rind];
                    rind++;
                }
                curr++;
            }
            while (lind < mid) {
                arr[curr] = left[lind];
                lind++;
                curr++;
            }
            while (rind < arr.length - mid) {
                arr[curr] = right[rind];
                rind++;
                curr++;
            }
        }
    }

    /**
     * Finds the longest number
     *
     * @param arr the array to be sorted
     * @return the required number
     */
    private static int maxLength(int[] arr) {
        int num = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                num = Integer.MAX_VALUE;
            }
            if (Math.abs(arr[i]) > num) {
                num = Math.abs(arr[i]);
            }
        }
        return num;
    }

    /**
     * adds elements of the array to buckets given a specific digit number
     *
     * @param main the array to be sorted
     * @param arr the array containing the buckets
     * @param max the digit number
     */
    private static void bucketing(int[] main,
                                  LinkedList<Integer>[] arr, int max) {
        for (int el: main) {
            if (el >= 0) {
                arr[(el / max) % 10 + 10].add(el);
            } else {
                arr[9 + (el / max) % 10].add(el);
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (arr.length > 0) {
            LinkedList<Integer>[] array = new LinkedList[20];
            for (int i = 0; i < array.length; i++) {
                array[i] = new LinkedList<>();
            }
            int max = maxLength(arr);
            int div = 1;
            while (max > 0) {
                max = max / 10;
                bucketing(arr, array, div);
                div *= 10;
                int j = 0;
                for (int k = 0; k < array.length; k++) {
                    while (array[k].size() > 0) {
                        arr[j] = array[k].remove();
                        j++;
                    }
                }
            }
        }
    }
}
