package com.ft_ss.sotrowania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var lbl = findViewById<TextView>(R.id.wynlbl)
        var lbl2 = findViewById<TextView>(R.id.srtlbl)
        var tab : IntArray = intArrayOf()

        fun removeLastItem(array: IntArray): IntArray {
            return array.copyOf(array.lastIndex)
        }

        fun czysc() {
            lbl.text = ""
            lbl2.text = ""

            for (i in 0..tab.size - 1) {
                tab = removeLastItem(tab)
            }
        }

        fun wypisz() {
            for (i in 0 until (tab.size)) {
                var text = lbl2.text
                lbl2.text = text.toString() + "  " + tab[i].toString()
            }
        }

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






        fun mergeOptimized(numArr: IntArray, start: Int, mid: Int, end: Int) {
            val leftArray = numArr.copyOfRange(start, mid)
            val rightArray = numArr.copyOfRange(mid, end)
            var i=0
            var j=0
            for(k in start until end) {
                if((j>=rightArray.size) || (i<leftArray.size && leftArray[i]<=rightArray[j])) {
                    numArr[k]=leftArray[i]
                    i++
                } else {
                    numArr[k]=rightArray[j]
                    j++
                }
            }
        }

        fun mergeSortInternalOptimized(numArr: IntArray, start: Int, end: Int) {
            if(start>=end)
                return
            val mid = (start+end)/2
            mergeSortInternalOptimized(numArr,start, mid)
            mergeSortInternalOptimized(numArr,mid+1,end)
            mergeOptimized(numArr,start,mid,end)
        }

        fun mergeSortOptimized(numArr: IntArray) {
            mergeSortInternalOptimized(numArr,0,numArr.size)
            wypisz()
        }




        fun losuj(ile: Int) {
            czysc()
            for (i in 0..ile - 1) {
                tab += (1..9).random()
            }

            for (i in 0 until (tab.size)) {
                var text = lbl.text
                lbl.text = text.toString() + "  " + tab[i].toString()
            }

            mergeSortOptimized(tab)
        }


        findViewById<Button>(R.id.button).setOnClickListener {
            losuj(findViewById<EditText>(R.id.ilee).text.toString().toInt())
        }
    }
}