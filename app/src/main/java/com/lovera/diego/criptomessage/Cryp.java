package com.lovera.diego.criptomessage;

/**
 * Created by Diego Lovera on 15/08/2017.
 */

class Cryp {
    private static char[] ABC = new char[83];
    private final int _CantChar = 83;

    private static void AsignarValorLetra() {
        ABC[0] = 'a';
        ABC[1] = 'b';
        ABC[2] = 'c';
        ABC[3] = 'd';
        ABC[4] = 'e';
        ABC[5] = 'f';
        ABC[6] = 'g';
        ABC[7] = 'h';
        ABC[8] = 'i';
        ABC[9] = 'j';
        ABC[10] = 'k';
        ABC[11] = 'l';
        ABC[12] = 'm';
        ABC[13] = 'n';
        ABC[14] = 'o';
        ABC[15] = 'p';
        ABC[16] = 'q';
        ABC[17] = 'r';
        ABC[18] = 's';
        ABC[19] = 't';
        ABC[20] = 'u';
        ABC[21] = 'v';
        ABC[22] = 'w';
        ABC[23] = 'x';
        ABC[24] = 'y';
        ABC[25] = 'z';
        ABC[26] = ' ';
        ABC[27] = ',';
        ABC[28] = '?';
        ABC[29] = '.';
        ABC[30] = 'A';
        ABC[31] = 'B';
        ABC[32] = 'C';
        ABC[33] = 'D';
        ABC[34] = 'E';
        ABC[35] = 'F';
        ABC[36] = 'G';
        ABC[37] = 'H';
        ABC[38] = 'I';
        ABC[39] = 'J';
        ABC[40] = 'K';
        ABC[41] = 'L';
        ABC[42] = 'M';
        ABC[43] = 'N';
        ABC[44] = 'O';
        ABC[45] = 'P';
        ABC[46] = 'Q';
        ABC[47] = 'R';
        ABC[48] = 'S';
        ABC[49] = 'T';
        ABC[50] = 'U';
        ABC[51] = 'V';
        ABC[52] = 'W';
        ABC[53] = 'X';
        ABC[54] = 'Y';
        ABC[55] = 'Z';
        ABC[56] = '@';
        ABC[57] = '1';
        ABC[58] = '2';
        ABC[59] = '3';
        ABC[60] = '4';
        ABC[61] = '5';
        ABC[62] = '6';
        ABC[63] = '7';
        ABC[64] = '8';
        ABC[65] = '9';
        ABC[66] = '0';
        ABC[67] = '¿';
        ABC[68] = '*';
        ABC[69] = '+';
        ABC[70] = '-';
        ABC[71] = '_';
        ABC[72] = '(';
        ABC[73] = ')';
        ABC[74] = '"';
        ABC[75] = '#';
        ABC[76] = '$';
        ABC[77] = '%';
        ABC[78] = '&';
        ABC[79] = '/';
        ABC[80] = '=';
        ABC[81] = 'ñ';
        ABC[82] = 'Ñ';
    }

    String Cifrar(String mensaje, String clave){
        char[] ClaveArray = clave.toCharArray();
        char[] MensajeArray = mensaje.toCharArray();
        int[] ClaveArrayNumero = new int[ClaveArray.length];
        int[] MensajeArrayNumero = new int[MensajeArray.length];
        int[] AumentoMensaje = new int[MensajeArray.length];
        char[] MensajeCifrado = new char[MensajeArray.length];

        AsignarValorLetra();
        for (int i = 0; i < ClaveArray.length; i++){
            for (int j = 0; j < _CantChar; j++)
            {
                if (ClaveArray[i] == ABC[j])
                {
                    ClaveArrayNumero[i] = j;
                }
            }
        }
        for (int i = 0; i < MensajeArray.length; i++){
            for (int j = 0; j < _CantChar; j++)
            {
                if (MensajeArray[i] == ABC[j])
                {
                    MensajeArrayNumero[i] = j;
                }
            }
        }
        int o = -1;
        int ola = ClaveArray.length;
        int ValorAumento = ClaveArray.length;
        for (int i = 0; i < MensajeArray.length; i++)
        {
            if (i == ola)
            {
                ola += ValorAumento;
                o = 0;
            }
            else
            {
                o += 1;
            }
            AumentoMensaje[i] = MensajeArrayNumero[i] + ClaveArrayNumero[o];
            if (AumentoMensaje[i] > (_CantChar-1))
            {
                AumentoMensaje[i] = AumentoMensaje[i] - _CantChar;
            }
        }
        for (int i = 0; i < AumentoMensaje.length; i++)
        {
            for (int j = 0; j < _CantChar; j++)
            {
                if (AumentoMensaje[i] == j)
                {
                    MensajeCifrado[i] = ABC[j];
                }
            }
        }

        String b = new String(MensajeCifrado);

        return b;
    }

    String Descifrar(String mensaje, String clave){
        char[] ClaveArray = clave.toCharArray();
        char[] MensajeArray = mensaje.toCharArray();
        int[] ClaveArrayNumero = new int[ClaveArray.length];
        int[] MensajeArrayNumero = new int[MensajeArray.length];
        int[] AumentoMensaje = new int[MensajeArray.length];
        char[] MensajeCifrado = new char[MensajeArray.length];
        AsignarValorLetra();
        for (int i = 0; i < ClaveArray.length; i++){
            for (int j = 0; j < _CantChar; j++)
            {
                if (ClaveArray[i] == ABC[j])
                {
                    ClaveArrayNumero[i] = j;
                }
            }
        }
        for (int i = 0; i < MensajeArray.length; i++){
            for (int j = 0; j < _CantChar; j++)
            {
                if (MensajeArray[i] == ABC[j])
                {
                    MensajeArrayNumero[i] = j;
                }
            }
        }
        int o = -1;
        int ola = ClaveArray.length;
        int ValorAumento = ClaveArray.length;
        for (int i = 0; i < MensajeArray.length; i++)
        {
            if (i == ola)
            {
                ola += ValorAumento;
                o = 0;
            }
            else
            {
                o += 1;
            }
            AumentoMensaje[i] = MensajeArrayNumero[i] - ClaveArrayNumero[o];
            if (AumentoMensaje[i] < 0)
            {
                AumentoMensaje[i] = AumentoMensaje[i] + _CantChar;
            }
        }
        for (int i = 0; i < AumentoMensaje.length; i++)
        {
            for (int j = 0; j < _CantChar; j++)
            {
                if (AumentoMensaje[i] == j)
                {
                    MensajeCifrado[i] = ABC[j];
                }
            }
        }

        String b = new String(MensajeCifrado);

        return b;
    }
}

