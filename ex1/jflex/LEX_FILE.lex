/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator		= \r|\n|\r\n
WhiteSpace			= [\ ] | [\t]
LPAREN				= \(
RPAREN				= \)
LBRACK				= \[
RBRACK				= \]
LBRACE				= \{
RBRACE				= \}
PLUS				= \+
MINUS				= \-
TIMES				= \*
DIVIDE				= \/
COMMA				= \,
DOT					= \.
SEMICOLON			= \;
ASSIGN				= :=
EQ					= \=
LT					= \<
GT					= \>
NIL					= nil
TYPE_INT			= int
TYPE_VOID			= void
TYPE_STRING			= string
CLASS				= class
ARRAY				= array
WHILE				= while
EXTENDS				= extends
RETURN				= return
NEW					= new
IF					= if
INT					= 0 | [1-9][0-9]*
BAD_ZERO			= 0{INT}
ID					= [a-zA-Z]+[a-zA-Z0-9]*
STRING				= [\"][a-zA-Z]*[\"]
COMMENT1_CHARS		= [a-zA-Z0-9\(\)\[\]\{\}\?\!\+\-\*\/\.\;] | {WhiteSpace}
COMMENT1			= [/][/]{COMMENT1_CHARS}*{LineTerminator}
COMMENT2_CHARS_WITHOUT_STAR = [a-zA-Z0-9\(\)\[\]\{\}\?\!\+\-\/\.\;] | {WhiteSpace} | {LineTerminator}
COMMENT2_CHARS_WITHOUT_SLASH = [a-zA-Z0-9\(\)\[\]\{\}\?\!\+\-\*\.\;] | {WhiteSpace} | {LineTerminator}
COMMENT2         = [/][\*]({COMMENT2_CHARS_WITHOUT_STAR} | (\*{COMMENT2_CHARS_WITHOUT_SLASH}))*[\*]+[/]
BAD_COMMENT         = [/][\*]


/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {

{LineTerminator}	{}
{WhiteSpace}		{}
{COMMENT1}			{}
{COMMENT2}       	{}
{BAD_COMMENT}       { return symbol(TokenNames.BAD_COMMENT);}
{LPAREN}			{ return symbol(TokenNames.LPAREN);}
{RPAREN}			{ return symbol(TokenNames.RPAREN);}
{LBRACK}			{ return symbol(TokenNames.LBRACK);}
{RBRACK}			{ return symbol(TokenNames.RBRACK);}
{LBRACE}			{ return symbol(TokenNames.LBRACE);}
{RBRACE}			{ return symbol(TokenNames.RBRACE);} 
{PLUS}				{ return symbol(TokenNames.PLUS); }  
{TIMES}				{ return symbol(TokenNames.TIMES); }
{MINUS}				{ return symbol(TokenNames.MINUS); }
{DIVIDE}			{ return symbol(TokenNames.DIVIDE); }
{COMMA}				{ return symbol(TokenNames.COMMA); }
{DOT}				{ return symbol(TokenNames.DOT); }
{SEMICOLON}			{ return symbol(TokenNames.SEMICOLON); }
{ASSIGN}			{ return symbol(TokenNames.ASSIGN); }
{EQ}				{ return symbol(TokenNames.EQ); }
{LT}				{ return symbol(TokenNames.LT); }
{GT}				{ return symbol(TokenNames.GT); }
{NIL}				{ return symbol(TokenNames.NIL); }
{TYPE_INT}			{ return symbol(TokenNames.TYPE_INT); }
{TYPE_VOID}			{ return symbol(TokenNames.TYPE_VOID); }
{TYPE_STRING}		{ return symbol(TokenNames.TYPE_STRING); }
{CLASS}				{ return symbol(TokenNames.CLASS); }
{ARRAY}				{ return symbol(TokenNames.ARRAY); }
{WHILE}				{ return symbol(TokenNames.WHILE); }
{EXTENDS}			{ return symbol(TokenNames.EXTENDS); }
{RETURN}			{ return symbol(TokenNames.RETURN); }
{NEW}				{ return symbol(TokenNames.NEW); }
{IF}				{ return symbol(TokenNames.IF); }
{INT}				{ return symbol(TokenNames.INT, Integer.valueOf(yytext())); }
{ID}				{ return symbol(TokenNames.ID, yytext()); }
{STRING}			{ return symbol(TokenNames.STRING, yytext()); }
{BAD_ZERO}			{ return symbol(TokenNames.BAD_ZERO);}
<<EOF>>				{ return symbol(TokenNames.EOF);}
}
