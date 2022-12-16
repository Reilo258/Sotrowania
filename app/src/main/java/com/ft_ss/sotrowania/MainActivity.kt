package com.ft_ss.sotrowania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.flow.merge

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tab : IntArray = intArrayOf()
        var heapSize = 0

        fun removeLastItem(array: IntArray): IntArray {
            return array.copyOf(array.lastIndex)
        }

        fun czysc() {
            for (i in 0..tab.size - 1) {
                tab = removeLastItem(tab)
            }
        }



        //          SORTOWANIE SZYBKIE / QUICKSORT

        fun swapArray(a: IntArray, b: Int, c: Int) {
            val temp = a[b]
            a[b] = a[c]
            a[c] = temp
        }
        fun partition(array: IntArray, l: Int, r: Int): Int {
            var left = l
            var right = r
            val pivot = array[(left + right)/2] // 4) Pivot Point
            while (left <= right) {
                while (array[left] < pivot) left++ // 5) Find the elements on left that should be on right

                while (array[right] > pivot) right-- // 6) Find the elements on right that should be on left

                // 7) Swap elements, and move left and right indices
                if (left <= right) {
                    swapArray(array, left,right)
                    left++
                    right--
                }
            }
            return left
        }
        fun quickSort(array: IntArray, left: Int, right: Int) {
            val index = partition(array, left, right)
            if(left < index-1) { // 2) Sorting left half
                quickSort(array, left, index-1)
            }
            if(index < right) { // 3) Sorting right half
                quickSort(array,index, right)
            }
        }





        //          SORTOWANIE BĄBELKOWE

        fun sortbb(tab : IntArray) { //działą
            for (i in 0 until (tab.size - 1)) {
                for (j in 0 until (tab.size - i - 1)) {
                    if (tab[j] > tab[j + 1]) {
                        val tmp = tab[j]
                        tab[j] = tab[j + 1]
                        tab[j + 1] = tmp
                    }
                }
            }
        }



        //          SORTOWANIE PRZEZ WSTAWIANIE

        fun wsta(tab: IntArray) { //działa
            for (count in 1..tab.count() - 1){
                val item = tab[count]
                var i = count
                while (i>0 && item < tab[i - 1]){
                    tab[i] = tab[i - 1]
                    i -= 1
                }
                tab[i] = item
            }
        }



        //          HEAPSORT

        fun swap(A: IntArray, i: Int, j: Int) {
            var temp = A[i]
            A[i] = A[j]
            A[j] = temp
        }

        fun max_heapify(A: IntArray, i: Int) {
            var l = 2*i;
            var r = 2*i+1;
            var largest: Int;

            if ((l <= heapSize - 1) && (A[l] > A[i])) {
                largest = l;
            } else
                largest = i

            if ((r <= heapSize - 1) && (A[r] > A[l])) {
                largest = r
            }

            if (largest != i) {
                swap(A, i, largest);
                max_heapify(A, largest);
            }
        }

        fun heap_sort(A: IntArray) {
            heapSize = A.size
            for (i in heapSize / 2 downTo 0) {
                max_heapify(A, i)
            }
            for (i in A.size - 1 downTo 1) {
                swap(A, i, 0)
                heapSize = heapSize - 1
                max_heapify(A, 0)

            }
        }



        //          SORTOWANIE PRZEZ SCALANIE / MERGESORT

        fun mergeHalvesIA(workA: IntArray,
                                          workB: IntArray,
                                          start: Int,
                                          mid: Int,
                                          exclusiveEnd: Int) {
            var p1 = start
            var p2 = mid
            for (i in start until exclusiveEnd) {
                if (p1 < mid && (p2 == exclusiveEnd || workA[p1] <= workA[p2])) {
                    workB[i] = workA[p1]
                    p1++
                } else {
                    workB[i] = workA[p2]
                    p2++
                }
            }
        }
        fun sortSectionIA(input: IntArray,
                                   output: IntArray,
                                   start: Int,
                                   exclusiveEnd: Int) : Unit {

            if (exclusiveEnd - start <= 1)
                return
            val mid = (start + exclusiveEnd) / 2
            sortSectionIA(output, input, start, mid)
            sortSectionIA(output, input, mid, exclusiveEnd)
            mergeHalvesIA(input, output, start, mid, exclusiveEnd)
        }
        fun mergeSortIA(array: IntArray) : IntArray {
            val arrayCopy = array.copyOf()
            val arrayCopy2 = array.copyOf()

            sortSectionIA(arrayCopy, arrayCopy2, 0, arrayCopy.size)

            return arrayCopy2
        }





        fun losuj(ile: Int, ktore: Int) {
            czysc()
            for (i in 0..ile - 1) {
                tab += (1..9).random()
            }

            var a: MutableList<Int> = mutableListOf()


            if(ktore == 1)
                quickSort(tab, 0, tab.size - 1)
            if(ktore == 2)
                wsta(tab)
            if(ktore == 3)
                sortbb(tab)
            if(ktore == 4)
                heap_sort(tab)
            else
                mergeSortIA(tab)


        }

        findViewById<Button>(R.id.button).setOnClickListener {
            var quickStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 1)
            var quickTime = System.currentTimeMillis() - quickStart;
            findViewById<TextView>(R.id.quicklbl).text = quickTime.toString() + " milisekund"

            var wstStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 2)
            var wstTime = System.currentTimeMillis() - wstStart;
            findViewById<TextView>(R.id.wstlbl).text = wstTime.toString() + " milisekund"

            var bbStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 3)
            var bbTime = System.currentTimeMillis() - bbStart;
            findViewById<TextView>(R.id.bablbl).text = bbTime.toString() + " milisekund"

            var heapStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 4)
            var heapTime = System.currentTimeMillis() - heapStart;
            findViewById<TextView>(R.id.heplbl).text = heapTime.toString() + " milisekund"

            var mergeStart = System.currentTimeMillis()
            for(i in 0..findViewById<EditText>(R.id.iler).text.toString().toInt())
                losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt(), 5)
            var mergeTime = System.currentTimeMillis() - mergeStart;
            findViewById<TextView>(R.id.scalbl).text = mergeTime.toString() + " milisekund"
        }
    }
}