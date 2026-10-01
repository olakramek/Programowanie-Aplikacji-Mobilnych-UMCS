import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val numberList = mutableListOf<Int>()

    fun addRandomNumber() {
        val randomNumber = (1..100).random()
        numberList.add(randomNumber)
        numberList.sort()
    }

    fun sortSecondFragmentDescending() {
        numberList.sortDescending()
    }

    fun sortThirdFragmentAscendingEven() {
        numberList.sortBy { it.takeIf { it % 2 == 0 } }
    }

    fun sortThirdFragmentAscendingOdd() {
        numberList.sortBy { it.takeIf { it % 2 != 0 } }
    }
    fun sortFourthFragmentDescendingOdd() {
        numberList.sortByDescending { it.takeIf { it % 2 != 0 } }
    }

    fun sortFourthFragmentDescendingEven() {
        numberList.sortByDescending { it.takeIf { it % 2 == 0 } }
    }
    fun calculateSum(): Int {
        return numberList.sum()
    }
}