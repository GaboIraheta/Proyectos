package com.proyectoPdm.seashellinc.data.model.balancer

import com.proyectoPdm.seashellinc.utils.checkedAddSum
import com.proyectoPdm.seashellinc.utils.checkedMultiply
import com.proyectoPdm.seashellinc.utils.gcd
import kotlin.math.sign

class Matrix(val numRows: Int, val numCols: Int) {
    private var cells: Array<IntArray>

    init {
        if (numRows < 0 || numCols < 0) throw IllegalArgumentException("Argumento ilegal")
        this.cells = Array(numRows) {
            IntArray(numCols) // { 0 }
        }
    }

    // Returns the value of the given cell in the matrix, where r is the row and c is the column.
    fun get (r: Int, c: Int): Int {
        if (r < 0 || r >= this.numRows || c < 0 || c >= this.numCols) {
            throw IllegalArgumentException("Índice fuera de rango")
        }
        return this.cells[r][c]
    }

    // Sets the given cell in the matrix to the given value, where r is the row and c is the column.
    fun set (r: Int, c: Int, value: Int) {
        if (r < 0 || r >= this.numRows || c < 0 || c >= this.numCols) {
            throw IllegalArgumentException("Índice fuera de rango")
        }
        this.cells[r][c] = value
    }

    private fun swapRows(i: Int, j: Int) {
        if (i < 0 || i >= this.numRows || j < 0 || j >= this.numRows) {
            throw IllegalArgumentException("Índice fuera de rango")
        }
        val temp: IntArray = this.cells[i]
        this.cells[i] = this.cells[j]
        this.cells[j] = temp
    }

    private companion object {
        fun addRows(x: IntArray, y: IntArray): IntArray {
            var z = mutableListOf<Int>()
            for (i in x.indices){
                z.add(checkedAddSum(x[i], y[i]).toInt())
            }
            return z.toIntArray()
        }

        fun multiplyRow(x: IntArray, c: Int): IntArray {
            return x.map { value -> checkedMultiply(value, c) }.toIntArray()
        }

        fun gcdRow(x: IntArray): Int {
            var result = 0
            for (value in x){
                result = gcd(value, result)
            }
            return result
        }

        fun simplifyRow(x: IntArray): IntArray {
            var sign = 0
            for (value in x) {
                if (value != 0) {
                    sign = value.sign
                    break
                }
            }
            if (sign == 0) return x
            val g = gcdRow(x) * sign
            return x.map {value -> value / g}.toIntArray()
        }
    }

    fun gaussJordanEliminate() {
        this.cells = this.cells.map { row -> simplifyRow(row) }.toTypedArray()
        var cells = this.cells

        var numPivots = 0
        for (i in 0 until this.numCols) {
            var pivotRow = numPivots
            while (pivotRow < this.numRows && cells[pivotRow][i] == 0) pivotRow++
            if (pivotRow == this.numRows) continue

            val pivot = cells[pivotRow][i]
            this.swapRows(numPivots, pivotRow)
            numPivots++

            for (j in numPivots until this.numRows){
                val g = gcd(pivot, cells[j][i])
                cells[j] = simplifyRow(
                    addRows(
                        multiplyRow(cells[j], pivot / g),
                        multiplyRow(cells[i], -cells[j][i] / g)
                    )
                )
            }
        }

        for (i in this.numRows - 1 downTo 0) {
            var pivotCol = 0
            while (pivotCol < this.numCols && cells[i][pivotCol] == 0) pivotCol++
            if (pivotCol == this.numCols) continue
            val pivot = cells[i][pivotCol]

            for (j in i - 1 downTo 0) {
                val g = gcd(pivot, cells[j][pivotCol])
                cells[j] = simplifyRow(
                    addRows(
                        multiplyRow(cells[j], pivot / g),
                        multiplyRow(cells[i], -cells[j][pivotCol] / g)
                    )
                )
            }
        }
    }
}