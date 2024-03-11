public class Main {
    public static void swap(int[] array, int index1, int index2) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Le tableau ne peut pas être vide ou nul.");
        }

        var size = array.length;
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) {
            throw new IllegalArgumentException("Paramètres invalides.");
        } else {
            if (index1 != index2) {
                var tmp = array[index1];
                array[index1] = array[index2];
                array[index2] = tmp;
            }
        }
    }

    public static int indexOfMin(int[] array, int start, int end) {
        if (array == null || array.length == 0 || start < 0 || start >= array.length || end <= start || end > array.length) {
            throw new IllegalArgumentException("Paramètres invalides.");
        }

        int indiceMin = start;
        for (int i = start + 1; i < end; i++) {
            if (array[i] < array[indiceMin]) {
                indiceMin = i;
            }
        }
        return indiceMin;
    }

    public static void sort(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Le tableau ne peut pas être vide ou nul.");
        }

        for (int i = 0; i < array.length - 1; i++) {
            int indiceMin = indexOfMin(array, i, array.length);
            swap(array, i, indiceMin);
        }
    }

    public static void main(String[] args) {
        int[] tab = {3, 1, 4, 2};
        sort(tab);

        for (int nb : tab) {
            System.out.println(nb);
        }
    }
}
