package com.diegolovera.simplecypher;

import com.diegolovera.simplecypher.Exceptions.EmptyListException;
import com.diegolovera.simplecypher.Exceptions.EmptyPasswordException;
import com.diegolovera.simplecypher.Exceptions.InvalidLetterException;

import java.util.ArrayList;
import java.util.List;

/**
 *  SimpleCypher implements a simple method of encryption to hide a message in a simple way and
 *  also implements the method to decrypt them as well. It's important to now that you should not
 *  use this class if you pretend to keep real important information safe, this is only for simple
 *  purposes like send a message that you don't want to be read that easily or things like that.
 */
public class SimpleCypher {
    // Required parameter
    private String mPassword;

    // Optional parameters
    private char[] mLetterList;

    /**
     * private constructor, it can only be reach by the class builder
     * @param builder it receives the builder
     */
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
        }
        return string.toString();
    }

    /**
     * Function to encode a message using the password set and the list of letters passed or the defaults.
     * The function takes the message and the password to then convert each letter of both into
     * it's relative value, then it will add the value of the first letter from the message plus
     * the value of the first letter of the password and so on. Because the message is going to be
     * probably bigger than the password the password is going to repeat until the message is fully
     * encrypted, this will create a pattern that could be easily identified, so please do not use
     * this method if you really need your information to be safe.
     * @param message String to be encrypted.
     * @return Returns the String of the message encrypted.
     * @throws InvalidLetterException If the letter in the message isn't defined in the
     * {@link SimpleCypher#mLetterList} then the encryption can't be done correctly so an exception will
     * be throw.
     * @throws EmptyPasswordException If the {@link SimpleCypher#mPassword} is empty then the
     * encryption can't be done so an exception will be throw.
     */
    public String encrypt(String message) throws InvalidLetterException, EmptyPasswordException {
        if (mPassword.isEmpty()) {
            throw new EmptyPasswordException("The password cannot be empty");
        }
        if (message.isEmpty()) {
            return "";
        }
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

    /**
     * Function to encode a message using the password set and the list of letters passed or the defaults.
     * The function takes the message and the password to then convert each letter of both into
     * it's relative value, then it will subtract the value of the first letter from the message minus
     * the value of the first letter of the password and so on. Because the message is going to be
     * probably bigger than the password the password is going to repeat until the message is fully
     * decrypted.
     * @param message String to be decrypted.
     * @return Returns the String of the message decrypted.
     * @throws InvalidLetterException If the letter in the message isn't defined in the
     * {@link SimpleCypher#mLetterList} then the decryption can't be done
     * correctly so an exception will be throw.
     * @throws EmptyPasswordException If the {@link SimpleCypher#mPassword} is empty then the
     * decryption can't be done so an exception will be throw.
     */
    public String decrypt(String message) throws InvalidLetterException, EmptyPasswordException {
        if (mPassword.isEmpty()) {
            throw new EmptyPasswordException("The password cannot be empty");
        }
        if (message.isEmpty()) {
            return "";
        }
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

    /**
     * Method to change the letter list.
     * @param letterList an array of letters to be use as valid characters
     *                   to be encrypted or decrypted.
     * @throws EmptyListException The list of letters must not be empty, if so then an exception
     * will be throw.
     */
    public void changeLetterList(char[] letterList) throws EmptyListException {
        if (letterList == null || letterList.length == 0) {
            throw new EmptyListException("The letter list must not be null or empty");
        }
        mLetterList = letterList;
    }

    /**
     * Method to change the password to be used.
     * @param password a String that will be the password.
     * @throws EmptyPasswordException The password must not be empty, if so then an exception will
     * be throw.
     */
    public void changePassword(String password) throws EmptyPasswordException {
        if (password.isEmpty()) {
            throw new EmptyPasswordException("The password cannot be empty");
        }
        mPassword = password;
    }

    /**
     * Builder class for the {@link SimpleCypher}. The password must be set and a list of valid
     * characters for the encryption can be set or the defaults can be used.
     */
    public static class SimpleCypherBuilder {
        private String password;
        private char[] letterList;

        /**
         * Generates the default characters to be used in case that none are set.
         */
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

        /**
         * Default constructor.
         * @param password a password must be assigned.
         */
        public SimpleCypherBuilder(String password) {
            this.password = password;
            defaultLetterList();
        }

        /**
         * Sets the character list to be used in the encryption.
         * @param letterList an array of chars, each of the characters should be unique.
         * @return the builder to nest more parameters.
         */
        public SimpleCypherBuilder setLetterList(char[] letterList) {
            this.letterList = letterList;
            return this;
        }

        /**
         * Build the instance of the {@link SimpleCypher} class.
         * @return a new instance with the parameters set ready to be used.
         */
        public SimpleCypher build() {
            return new SimpleCypher(this);
        }
    }
}

