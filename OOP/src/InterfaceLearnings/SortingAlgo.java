package InterfaceLearnings;

public class SortingAlgo {
    public static void main(String[] args) {
        Sortable bubbleSort = new BubbleSort();
        Sortable quickSort = new QuickSort();

        // call the sort method of each implementation
        bubbleSort.sort();
        quickSort.sort();
    }
}
