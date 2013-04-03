grammar Jornal;

// Lexer

BEGIN: 'begin';
END: 'end';
CONTENT: 'content';
NEWSPAPER: 'newspaper';
TITLE: 'title';
DATE: 'date';
SOURCE: 'source';
AUTHOR: 'author';
TEXT: 'text';
IMAGE: 'image';
ABSTRACT: 'abstract';
ITEM: 'item';
STRUCTURE: 'structure';
FORMAT: 'format';
COL: 'col';
BORDER: 'border';
LEFT_SQBRACKET: '[';
RIGHT_SQBRACKET: ']';
LEFT_BRACKET: '{';
RIGHT_BRACKET: '}';
COLON: ':';
UNDERSCORE: '_';
DOT: '.';

NUMBER: '0'..'9' '0'..'9'*;

LETTER: ('a'..'z') | ('A'..'Z');
DIGIT: '0'..'9';

WS: [ \t\n]+ -> skip; // skip spaces, tabs, newlines
NEWLINE: ('\r'? '\n')+;
SINGLE_COMMENT: '//' ~('\r' | '\n')* NEWLINE { skip(); };

STRING: '\"' (~('"') | '\\"')* '\"' { setText(getText().substring(1, getText().length()-1)); } ;
NEWSNAME: LETTER (LETTER | DIGIT | UNDERSCORE)*;

//Parser

r: BEGIN (content structure | structure content) END EOF;

content: CONTENT LEFT_BRACKET news* newspaper news* RIGHT_BRACKET;

newspaper: NEWSPAPER LEFT_BRACKET newspaper_fields* RIGHT_BRACKET;

news: NEWSNAME LEFT_BRACKET news_fields* RIGHT_BRACKET;

newspaper_fields: TITLE COLON STRING #NEWSPAPER_TITLE | DATE COLON STRING #NEWSPAPER_DATE;

news_fields: news_field_value COLON STRING;

news_field_value: (TITLE | ABSTRACT | IMAGE | SOURCE | DATE | AUTHOR | TEXT);

structure: STRUCTURE LEFT_BRACKET item*? format item*? RIGHT_BRACKET;

item: ITEM LEFT_SQBRACKET (NUMBER | pair_number) RIGHT_SQBRACKET LEFT_BRACKET news_piece* RIGHT_BRACKET;

format: FORMAT LEFT_BRACKET ((border column) | (column border)) RIGHT_BRACKET;

border: BORDER COLON NUMBER;

column: COL COLON NUMBER;

pair_number: NUMBER COLON NUMBER;

news_piece: NEWSNAME DOT news_field_value;
