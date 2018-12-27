/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.lexer;

import java.io.*;
import java.util.*;
import minipython.node.*;

public class Lexer
{
    protected Token token;
    protected State state = State.INITIAL;

    private PushbackReader in;
    private int line;
    private int pos;
    private boolean cr;
    private boolean eof;
    private final StringBuffer text = new StringBuffer();

    protected void filter() throws LexerException, IOException
    {
    }

    public Lexer(PushbackReader in)
    {
        this.in = in;

        if(gotoTable == null)
        {
            try
            {
                DataInputStream s = new DataInputStream(
                    new BufferedInputStream(
                    Lexer.class.getResourceAsStream("lexer.dat")));

                // read gotoTable
                int length = s.readInt();
                gotoTable = new int[length][][][];
                for(int i = 0; i < gotoTable.length; i++)
                {
                    length = s.readInt();
                    gotoTable[i] = new int[length][][];
                    for(int j = 0; j < gotoTable[i].length; j++)
                    {
                        length = s.readInt();
                        gotoTable[i][j] = new int[length][3];
                        for(int k = 0; k < gotoTable[i][j].length; k++)
                        {
                            for(int l = 0; l < 3; l++)
                            {
                                gotoTable[i][j][k][l] = s.readInt();
                            }
                        }
                    }
                }

                // read accept
                length = s.readInt();
                accept = new int[length][];
                for(int i = 0; i < accept.length; i++)
                {
                    length = s.readInt();
                    accept[i] = new int[length];
                    for(int j = 0; j < accept[i].length; j++)
                    {
                        accept[i][j] = s.readInt();
                    }
                }

                s.close();
            }
            catch(Exception e)
            {
                throw new RuntimeException("The file \"lexer.dat\" is either missing or corrupted.");
            }
        }
    }

    public Token peek() throws LexerException, IOException
    {
        while(token == null)
        {
            token = getToken();
            filter();
        }

        return token;
    }

    public Token next() throws LexerException, IOException
    {
        while(token == null)
        {
            token = getToken();
            filter();
        }

        Token result = token;
        token = null;
        return result;
    }

    protected Token getToken() throws IOException, LexerException
    {
        int dfa_state = 0;

        int start_pos = pos;
        int start_line = line;

        int accept_state = -1;
        int accept_token = -1;
        int accept_length = -1;
        int accept_pos = -1;
        int accept_line = -1;

        int[][][] gotoTable = this.gotoTable[state.id()];
        int[] accept = this.accept[state.id()];
        text.setLength(0);

        while(true)
        {
            int c = getChar();

            if(c != -1)
            {
                switch(c)
                {
                case 10:
                    if(cr)
                    {
                        cr = false;
                    }
                    else
                    {
                        line++;
                        pos = 0;
                    }
                    break;
                case 13:
                    line++;
                    pos = 0;
                    cr = true;
                    break;
                default:
                    pos++;
                    cr = false;
                    break;
                };

                text.append((char) c);

                do
                {
                    int oldState = (dfa_state < -1) ? (-2 -dfa_state) : dfa_state;

                    dfa_state = -1;

                    int[][] tmp1 =  gotoTable[oldState];
                    int low = 0;
                    int high = tmp1.length - 1;

                    while(low <= high)
                    {
                        int middle = (low + high) / 2;
                        int[] tmp2 = tmp1[middle];

                        if(c < tmp2[0])
                        {
                            high = middle - 1;
                        }
                        else if(c > tmp2[1])
                        {
                            low = middle + 1;
                        }
                        else
                        {
                            dfa_state = tmp2[2];
                            break;
                        }
                    }
                }while(dfa_state < -1);
            }
            else
            {
                dfa_state = -1;
            }

            if(dfa_state >= 0)
            {
                if(accept[dfa_state] != -1)
                {
                    accept_state = dfa_state;
                    accept_token = accept[dfa_state];
                    accept_length = text.length();
                    accept_pos = pos;
                    accept_line = line;
                }
            }
            else
            {
                if(accept_state != -1)
                {
                    switch(accept_token)
                    {
                    case 0:
                        {
                            Token token = new0(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 1:
                        {
                            Token token = new1(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 2:
                        {
                            Token token = new2(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 3:
                        {
                            Token token = new3(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 4:
                        {
                            Token token = new4(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 5:
                        {
                            Token token = new5(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 6:
                        {
                            Token token = new6(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 7:
                        {
                            Token token = new7(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 8:
                        {
                            Token token = new8(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 9:
                        {
                            Token token = new9(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 10:
                        {
                            Token token = new10(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 11:
                        {
                            Token token = new11(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 12:
                        {
                            Token token = new12(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 13:
                        {
                            Token token = new13(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 14:
                        {
                            Token token = new14(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 15:
                        {
                            Token token = new15(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 16:
                        {
                            Token token = new16(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 17:
                        {
                            Token token = new17(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 18:
                        {
                            Token token = new18(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 19:
                        {
                            Token token = new19(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 20:
                        {
                            Token token = new20(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 21:
                        {
                            Token token = new21(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 22:
                        {
                            Token token = new22(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 23:
                        {
                            Token token = new23(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 24:
                        {
                            Token token = new24(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 25:
                        {
                            Token token = new25(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 26:
                        {
                            Token token = new26(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 27:
                        {
                            Token token = new27(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 28:
                        {
                            Token token = new28(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 29:
                        {
                            Token token = new29(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 30:
                        {
                            Token token = new30(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 31:
                        {
                            Token token = new31(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 32:
                        {
                            Token token = new32(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 33:
                        {
                            Token token = new33(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 34:
                        {
                            Token token = new34(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 35:
                        {
                            Token token = new35(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 36:
                        {
                            Token token = new36(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 37:
                        {
                            Token token = new37(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 38:
                        {
                            Token token = new38(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 39:
                        {
                            Token token = new39(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 40:
                        {
                            Token token = new40(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 41:
                        {
                            Token token = new41(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 42:
                        {
                            Token token = new42(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 43:
                        {
                            Token token = new43(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 44:
                        {
                            Token token = new44(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 45:
                        {
                            Token token = new45(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    case 46:
                        {
                            Token token = new46(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            pos = accept_pos;
                            line = accept_line;
                            return token;
                        }
                    }
                }
                else
                {
                    if(text.length() > 0)
                    {
                        throw new LexerException(
                            "[" + (start_line + 1) + "," + (start_pos + 1) + "]" +
                            " Unknown token: " + text);
                    }
                    else
                    {
                        EOF token = new EOF(
                            start_line + 1,
                            start_pos + 1);
                        return token;
                    }
                }
            }
        }
    }

    Token new0(String text, int line, int pos) { return new TTab(text, line, pos); }
    Token new1(int line, int pos) { return new TPlus(line, pos); }
    Token new2(int line, int pos) { return new TMinus(line, pos); }
    Token new3(int line, int pos) { return new TMult(line, pos); }
    Token new4(int line, int pos) { return new TDmult(line, pos); }
    Token new5(int line, int pos) { return new TMineq(line, pos); }
    Token new6(int line, int pos) { return new TDiveq(line, pos); }
    Token new7(int line, int pos) { return new TDiv(line, pos); }
    Token new8(int line, int pos) { return new TDot(line, pos); }
    Token new9(int line, int pos) { return new TEq(line, pos); }
    Token new10(int line, int pos) { return new TDeq(line, pos); }
    Token new11(int line, int pos) { return new TExclam(line, pos); }
    Token new12(int line, int pos) { return new TDictt(line, pos); }
    Token new13(int line, int pos) { return new TDef(line, pos); }
    Token new14(int line, int pos) { return new TLogicPlus(line, pos); }
    Token new15(int line, int pos) { return new TLPar(line, pos); }
    Token new16(int line, int pos) { return new TRPar(line, pos); }
    Token new17(int line, int pos) { return new TLBr(line, pos); }
    Token new18(int line, int pos) { return new TRBr(line, pos); }
    Token new19(int line, int pos) { return new TComma(line, pos); }
    Token new20(int line, int pos) { return new TQmark(line, pos); }
    Token new21(int line, int pos) { return new TGqmark(line, pos); }
    Token new22(int line, int pos) { return new TIf(line, pos); }
    Token new23(int line, int pos) { return new TElif(line, pos); }
    Token new24(int line, int pos) { return new TElse(line, pos); }
    Token new25(int line, int pos) { return new TWhile(line, pos); }
    Token new26(int line, int pos) { return new TFor(line, pos); }
    Token new27(int line, int pos) { return new TFrom(line, pos); }
    Token new28(int line, int pos) { return new TImp(line, pos); }
    Token new29(int line, int pos) { return new TAssert(line, pos); }
    Token new30(int line, int pos) { return new TAs(line, pos); }
    Token new31(int line, int pos) { return new TPrint(line, pos); }
    Token new32(int line, int pos) { return new TReturn(line, pos); }
    Token new33(int line, int pos) { return new TIn(line, pos); }
    Token new34(int line, int pos) { return new TLess(line, pos); }
    Token new35(int line, int pos) { return new TGreat(line, pos); }
    Token new36(int line, int pos) { return new TDif(line, pos); }
    Token new37(int line, int pos) { return new TTrue(line, pos); }
    Token new38(int line, int pos) { return new TSemi(line, pos); }
    Token new39(int line, int pos) { return new TFalse(line, pos); }
    Token new40(int line, int pos) { return new TQuote(line, pos); }
    Token new41(String text, int line, int pos) { return new TBlank(text, line, pos); }
    Token new42(String text, int line, int pos) { return new TLineComment(text, line, pos); }
    Token new43(String text, int line, int pos) { return new TId(text, line, pos); }
    Token new44(String text, int line, int pos) { return new TFloat(text, line, pos); }
    Token new45(String text, int line, int pos) { return new TInteger(text, line, pos); }
    Token new46(String text, int line, int pos) { return new TString(text, line, pos); }

    private int getChar() throws IOException
    {
        if(eof)
        {
            return -1;
        }

        int result = in.read();

        if(result == -1)
        {
            eof = true;
        }

        return result;
    }

    private void pushBack(int acceptLength) throws IOException
    {
        int length = text.length();
        for(int i = length - 1; i >= acceptLength; i--)
        {
            eof = false;

            in.unread(text.charAt(i));
        }
    }

    protected void unread(Token token) throws IOException
    {
        String text = token.getText();
        int length = text.length();

        for(int i = length - 1; i >= 0; i--)
        {
            eof = false;

            in.unread(text.charAt(i));
        }

        pos = token.getPos() - 1;
        line = token.getLine() - 1;
    }

    private String getText(int acceptLength)
    {
        StringBuffer s = new StringBuffer(acceptLength);
        for(int i = 0; i < acceptLength; i++)
        {
            s.append(text.charAt(i));
        }

        return s.toString();
    }

    private static int[][][][] gotoTable;
/*  {
        { // INITIAL
            {{9, 9, 1}, {10, 10, 2}, {13, 13, 3}, {32, 32, 4}, {33, 33, 5}, {34, 34, 6}, {35, 35, 7}, {38, 38, 8}, {39, 39, 9}, {40, 40, 10}, {41, 41, 11}, {42, 42, 12}, {43, 43, 13}, {44, 44, 14}, {45, 45, 15}, {46, 46, 16}, {47, 47, 17}, {48, 48, 18}, {49, 57, 19}, {58, 58, 20}, {59, 59, 21}, {60, 60, 22}, {61, 61, 23}, {62, 62, 24}, {63, 63, 25}, {65, 90, 26}, {91, 91, 27}, {93, 93, 28}, {97, 97, 29}, {98, 99, 30}, {100, 100, 31}, {101, 101, 32}, {102, 102, 33}, {103, 104, 30}, {105, 105, 34}, {106, 111, 30}, {112, 112, 35}, {113, 113, 30}, {114, 114, 36}, {115, 115, 30}, {116, 116, 37}, {117, 118, 30}, {119, 119, 38}, {120, 122, 30}, },
            {},
            {},
            {},
            {},
            {{61, 61, 39}, },
            {{32, 32, 40}, {34, 34, 41}, {65, 90, 42}, {97, 122, 43}, },
            {{0, 9, 44}, {10, 10, 45}, {11, 12, 44}, {13, 13, 46}, {14, 127, 44}, },
            {{38, 38, 47}, },
            {{32, 32, 48}, {39, 39, 49}, {65, 90, 50}, {97, 122, 51}, },
            {},
            {},
            {{42, 42, 52}, },
            {},
            {},
            {{61, 61, 53}, },
            {{48, 57, 54}, },
            {{61, 61, 55}, },
            {{46, 46, 56}, {48, 57, 57}, },
            {{46, 46, 56}, {48, 57, 58}, },
            {},
            {},
            {},
            {{61, 61, 59}, },
            {},
            {},
            {{48, 57, 60}, {65, 90, 61}, {97, 122, 62}, },
            {},
            {},
            {{48, 90, -28}, {97, 114, 62}, {115, 115, 63}, {116, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 90, -28}, {97, 100, 62}, {101, 101, 64}, {102, 104, 62}, {105, 105, 65}, {106, 122, 62}, },
            {{48, 90, -28}, {97, 107, 62}, {108, 108, 66}, {109, 122, 62}, },
            {{48, 90, -28}, {97, 97, 67}, {98, 110, 62}, {111, 111, 68}, {112, 113, 62}, {114, 114, 69}, {115, 122, 62}, },
            {{48, 90, -28}, {97, 101, 62}, {102, 102, 70}, {103, 108, 62}, {109, 109, 71}, {110, 110, 72}, {111, 122, 62}, },
            {{48, 90, -28}, {97, 113, 62}, {114, 114, 73}, {115, 122, 62}, },
            {{48, 100, -33}, {101, 101, 74}, {102, 122, 62}, },
            {{48, 113, -37}, {114, 114, 75}, {115, 122, 62}, },
            {{48, 90, -28}, {97, 103, 62}, {104, 104, 76}, {105, 122, 62}, },
            {},
            {{32, 122, -8}, },
            {},
            {{32, 122, -8}, },
            {{32, 122, -8}, },
            {{0, 127, -9}, },
            {},
            {{10, 10, 77}, },
            {},
            {{32, 122, -11}, },
            {},
            {{32, 122, -11}, },
            {{32, 122, -11}, },
            {},
            {},
            {{48, 57, 54}, },
            {},
            {{48, 57, 54}, },
            {{46, 57, -20}, },
            {{46, 57, -21}, },
            {},
            {{48, 122, -28}, },
            {{48, 122, -28}, },
            {{48, 122, -28}, },
            {{48, 114, -31}, {115, 115, 78}, {116, 122, 62}, },
            {{48, 101, -36}, {102, 102, 79}, {103, 122, 62}, },
            {{48, 90, -28}, {97, 98, 62}, {99, 99, 80}, {100, 122, 62}, },
            {{48, 90, -28}, {97, 104, 62}, {105, 105, 81}, {106, 114, 62}, {115, 115, 82}, {116, 122, 62}, },
            {{48, 107, -34}, {108, 108, 83}, {109, 122, 62}, },
            {{48, 113, -37}, {114, 114, 84}, {115, 122, 62}, },
            {{48, 90, -28}, {97, 110, 62}, {111, 111, 85}, {112, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 90, -28}, {97, 111, 62}, {112, 112, 86}, {113, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 104, -68}, {105, 105, 87}, {106, 122, 62}, },
            {{48, 90, -28}, {97, 115, 62}, {116, 116, 88}, {117, 122, 62}, },
            {{48, 90, -28}, {97, 116, 62}, {117, 117, 89}, {118, 122, 62}, },
            {{48, 104, -68}, {105, 105, 90}, {106, 122, 62}, },
            {},
            {{48, 100, -33}, {101, 101, 91}, {102, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 115, -76}, {116, 116, 92}, {117, 122, 62}, },
            {{48, 101, -36}, {102, 102, 93}, {103, 122, 62}, },
            {{48, 100, -33}, {101, 101, 94}, {102, 122, 62}, },
            {{48, 114, -31}, {115, 115, 95}, {116, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 90, -28}, {97, 108, 62}, {109, 109, 96}, {110, 122, 62}, },
            {{48, 110, -71}, {111, 111, 97}, {112, 122, 62}, },
            {{48, 90, -28}, {97, 109, 62}, {110, 110, 98}, {111, 122, 62}, },
            {{48, 116, -77}, {117, 117, 99}, {118, 122, 62}, },
            {{48, 100, -33}, {101, 101, 100}, {102, 122, 62}, },
            {{48, 107, -34}, {108, 108, 101}, {109, 122, 62}, },
            {{48, 113, -37}, {114, 114, 102}, {115, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 122, -28}, },
            {{48, 122, -28}, },
            {{48, 100, -33}, {101, 101, 103}, {102, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 113, -37}, {114, 114, 104}, {115, 122, 62}, },
            {{48, 115, -76}, {116, 116, 105}, {117, 122, 62}, },
            {{48, 113, -37}, {114, 114, 106}, {115, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 100, -33}, {101, 101, 107}, {102, 122, 62}, },
            {{48, 115, -76}, {116, 116, 108}, {117, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 115, -76}, {116, 116, 109}, {117, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 109, -89}, {110, 110, 110}, {111, 122, 62}, },
            {{48, 122, -28}, },
            {{48, 122, -28}, },
            {{48, 122, -28}, },
            {{48, 122, -28}, },
        }
    };*/

    private static int[][] accept;
/*  {
        // INITIAL
        {-1, 0, 41, 41, 41, 11, 40, -1, -1, -1, 15, 16, 3, 1, 19, 2, 8, 7, 45, 45, 38, 21, 34, 9, 35, 20, 43, 17, 18, 43, 43, 43, 43, 43, 43, 43, 43, 43, 43, 36, -1, 46, -1, -1, -1, 42, 42, 14, -1, 46, -1, -1, 4, 5, 44, 6, -1, -1, 45, 10, 43, 43, 43, 30, 43, 43, 43, 43, 43, 43, 22, 43, 33, 43, 43, 43, 43, 42, 43, 13, 43, 43, 43, 43, 26, 43, 43, 43, 43, 43, 43, 43, 12, 23, 24, 43, 27, 43, 43, 43, 37, 43, 43, 39, 43, 31, 43, 25, 29, 28, 32, },

    };*/

    public static class State
    {
        public final static State INITIAL = new State(0);

        private int id;

        private State(int id)
        {
            this.id = id;
        }

        public int id()
        {
            return id;
        }
    }
}