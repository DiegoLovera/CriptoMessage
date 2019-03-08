package com.diegolovera.simplecypher;

import com.diegolovera.simplecypher.Exceptions.InvalidLetterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Lovera on 15/08/2017.
 */

public class SimpleCypher {
    // Required parameter
    private String mPassword;

    // Optional parameters
    private char[] mLetterList;

    private SimpleCypher(SimpleCypherBuilder builder) {
        mPassword = builder.password;
        mLetterList = builder.letterList;
    }

    private List<Integer> assignValueToArray(char[] originalList) {
        List<Integer> valueList = new ArrayList<>();
        // Recorro cada letra de la contraseña para buscar su valor en el arreglo de las letras y
        // lo asigno a otro arreglo que va a contener el valor de cada letra
        for (char letter : originalList) {
            for (int j = 0; j < mLetterList.length; j++) {
                if (letter == mLetterList[j]) {
                    valueList.add(j);
                }
            }
        }
        // Si la lista original es más grande que la nueva entonces hay caracteres que noe estan
        if (originalList.length > valueList.size()) {
            return null;
        }
        return valueList;
    }

    private String assignLetterToValue(int[] originalList) {
        StringBuilder string = new StringBuilder();
        for (int value : originalList) {
            string.append(mLetterList[value]);
            /*
            for (int i = 0; i < mLetterList.length; i++) {
                if (value == i) {
                    string.append(mLetterList[i]);
                }
            }
            */
        }
        return string.toString();
    }

    public String encode(String message) throws InvalidLetterException {
        // Convert password and message to a char array
        char[] passwordArray = mPassword.toCharArray();
        char[] messageArray = message.toCharArray();
        // Create a new array that will carry the value of each letter
        List<Integer> passwordValueArray;
        List<Integer> messageValueArray;
        int[] messageOffset = new int[messageArray.length];

        passwordValueArray = assignValueToArray(passwordArray);
        if (passwordValueArray == null) {
            throw new InvalidLetterException("A letter or more in the password are not in the list defined, please change the word or reassign the list");
        }

        messageValueArray = assignValueToArray(messageArray);
        if (messageValueArray == null) {
            throw new InvalidLetterException("A letter or more in the word are not in the list defined, please change the word or reassign the list");
        }

        // Ahora es necesario recorrer el arreglo del mensaje completo e ir sumando los caracteres de la contraseña
        int passwordIndex = -1;
        int currentCicle = passwordArray.length;
        for (int i = 0; i < messageArray.length; i++) {
            // La contraseña suele ser más corta que el mensaje por lo que tiene que reptirse el index del arreglo de la contraseña
            if (i == currentCicle) {
                currentCicle += passwordArray.length;
                passwordIndex = 0;
            } else {
                passwordIndex += 1;
            }
            // Sumamos los valores del mensaje y la contraseña
            messageOffset[i] = messageValueArray.get(i) + passwordValueArray.get(passwordIndex);
            // Si el valor sobre pasa la cantidad de letras entonces es necesario regresarlo
            if (messageOffset[i] > (mLetterList.length - 1)) {
                messageOffset[i] = messageOffset[i] -  mLetterList.length;
            }
        }

        return assignLetterToValue(messageOffset);
    }

    public String decode(String message) throws InvalidLetterException {
        char[] passwordArray = mPassword.toCharArray();
        char[] messageArray = message.toCharArray();
        List<Integer> passwordValueArray;
        List<Integer> messageValueArray;
        int[] messageOffset = new int[messageArray.length];

        passwordValueArray = assignValueToArray(passwordArray);
        if (passwordValueArray == null) {
            throw new InvalidLetterException("A letter or more in the password are not in the list defined, please change the word or reassign the list");
        }

        messageValueArray = assignValueToArray(messageArray);
        if (messageValueArray == null) {
            throw new InvalidLetterException("A letter or more in the word are not in the list defined, please change the word or reassign the list");
        }

        int passwordIndex = -1;
        int currentCicle = passwordArray.length;
        for (int i = 0; i < messageArray.length; i++) {
            // La contraseña suele ser más corta que el mensaje por lo que tiene que reptirse el index del arreglo de la contraseña
            if (i == currentCicle) {
                currentCicle += passwordArray.length;
                passwordIndex = 0;
            } else {
                passwordIndex += 1;
            }
            // Sumamos los valores del mensaje y la contraseña
            messageOffset[i] = messageValueArray.get(i) - passwordValueArray.get(passwordIndex);
            // Si el valor sobre pasa la cantidad de letras entonces es necesario regresarlo
            if (messageOffset[i] < 0) {
                messageOffset[i] = messageOffset[i] +  mLetterList.length;
            }
        }

        return assignLetterToValue(messageOffset);
    }

    public void setLetterList(char[] letterList) {
        mLetterList = letterList;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public static class SimpleCypherBuilder {
        private String password;
        private char[] letterList;

        private void defaultLetterList() {
            letterList = new char[83];
            letterList[0] = 'a';
            letterList[1] = 'b';
            letterList[2] = 'c';
            letterList[3] = 'd';
            letterList[4] = 'e';
            letterList[5] = 'f';
            letterList[6] = 'g';
            letterList[7] = 'h';
            letterList[8] = 'i';
            letterList[9] = 'j';
            letterList[10] = 'k';
            letterList[11] = 'l';
            letterList[12] = 'm';
            letterList[13] = 'n';
            letterList[14] = 'o';
            letterList[15] = 'p';
            letterList[16] = 'q';
            letterList[17] = 'r';
            letterList[18] = 's';
            letterList[19] = 't';
            letterList[20] = 'u';
            letterList[21] = 'v';
            letterList[22] = 'w';
            letterList[23] = 'x';
            letterList[24] = 'y';
            letterList[25] = 'z';
            letterList[26] = ' ';
            letterList[27] = ',';
            letterList[28] = '?';
            letterList[29] = '.';
            letterList[30] = 'A';
            letterList[31] = 'B';
            letterList[32] = 'C';
            letterList[33] = 'D';
            letterList[34] = 'E';
            letterList[35] = 'F';
            letterList[36] = 'G';
            letterList[37] = 'H';
            letterList[38] = 'I';
            letterList[39] = 'J';
            letterList[40] = 'K';
            letterList[41] = 'L';
            letterList[42] = 'M';
            letterList[43] = 'N';
            letterList[44] = 'O';
            letterList[45] = 'P';
            letterList[46] = 'Q';
            letterList[47] = 'R';
            letterList[48] = 'S';
            letterList[49] = 'T';
            letterList[50] = 'U';
            letterList[51] = 'V';
            letterList[52] = 'W';
            letterList[53] = 'X';
            letterList[54] = 'Y';
            letterList[55] = 'Z';
            letterList[56] = '@';
            letterList[57] = '1';
            letterList[58] = '2';
            letterList[59] = '3';
            letterList[60] = '4';
            letterList[61] = '5';
            letterList[62] = '6';
            letterList[63] = '7';
            letterList[64] = '8';
            letterList[65] = '9';
            letterList[66] = '0';
            letterList[67] = '¿';
            letterList[68] = '*';
            letterList[69] = '+';
            letterList[70] = '-';
            letterList[71] = '_';
            letterList[72] = '(';
            letterList[73] = ')';
            letterList[74] = '"';
            letterList[75] = '#';
            letterList[76] = '$';
            letterList[77] = '%';
            letterList[78] = '&';
            letterList[79] = '/';
            letterList[80] = '=';
            letterList[81] = 'ñ';
            letterList[82] = 'Ñ';
        }

        public SimpleCypherBuilder(String password) {
            this.password = password;
            defaultLetterList();
        }

        public SimpleCypherBuilder setLetterList(char[] letterList) {
            this.letterList = letterList;
            return this;
        }

        public SimpleCypher build() {
            return new SimpleCypher(this);
        }
    }
}

