package com.example.unblockme10x10;

public class InputStringParser {
    // Gets input string and analyzes it and returns a /////////////////2d array with numbers that show what block is related to each section
    public static int[] extractBlocksInfoFromInput(String input) {

        // Doing some Operations on inputString
        String initialState = input;
        initialState = initialState.substring(initialState.indexOf("[")+1, initialState.indexOf("]"));    // Replacing the whole inputString with only the part inside "[...]"
        initialState = initialState.replaceAll("\\s", "");    // Deleting whitespaces
        initialState = initialState.replace("}", "");    // Deleting "}" characters
        String[] array = initialState.split(",");    // Splitting the string by "," char and putting them in a srtring array. (Even elements in the array will contain section number and odds will contain block number related to the section in previous element)
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].substring(array[i].indexOf("=")+1);    // Replacing the whole element with only the number part (which is still srting)
        }

        // Extracting needed int numbers of sections and blocks and putting them in a 2D array. Each element contains an array with size of 2. First element of this array is section number and second is its block number
        int[][] array2D = new int[array.length/2][2];   // Size of array2D is half of b becouse every element of it contains a pair of elements of b
        for (int i = 0; i < array.length; i += 2)    // The step is 2 because section numbers and block numbers are pair in b array
        {
            //          try{
            array2D[ i/2 ][0] = Integer.parseInt( array[i] );      // Converting string number (which is section number) to int number and storing in the first element of each array element of array2D (if possible)
            array2D[ i/2 ][1] = Integer.parseInt( array[i+1] );      // Doing the same as above to block number
            //          } catch (NumberFormatException e) {
            //!!
            //          }
        }

        // Putting info of an unknown sized array into an array of size 100
        // The index of each element in the array is considered as section number and the element is nimber of the block set to the section.
        int[] blocksState = new int[100];
        for (int i=0; i<100; i++) {
            boolean hasBlock = false;
            for (int[] j : array2D) {
                if (i == j[0]) {
                    blocksState[i] = j[1];
                    hasBlock = true;
                    break;
                }
            }
            // If a section has no block number, its block number will be set 0
            if (hasBlock != true) {
                blocksState[i] = 0;
            }
        }
        return blocksState;
    }

    // Gets inputString and extracts and returns exit Section
    public static int extractExitSecFromInput(String input) {

        // Input contains two parts. First part is info about blocks, second part is about exit section.
        // splitting the input parts based of end of first part (the "]" sign) and taking the second part to extract exit section.
        String exitSecString = input;
        exitSecString = exitSecString.substring(exitSecString.indexOf("/")+1);

        exitSecString = exitSecString.replaceAll("\\s", "");    // Deleting whitespaces
        exitSecString = exitSecString.substring(exitSecString.indexOf("[")+1, exitSecString.indexOf("]"));      // Replacing the string with only the part inside "[....]".
        exitSecString = exitSecString.substring(exitSecString.indexOf("=")+1);      // Replacing the string with only numeric part of it (Which is after "=").

        int exitSecInt = Integer.parseInt( exitSecString );     // Converting the number (exit sec) from string to int.

        return exitSecInt;
    }

}
