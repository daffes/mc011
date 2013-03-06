// Define a grammar called Hello
// match keyword hello followed by an identifier
// match lower-case identifiers
grammar Jornal;
 
r: 'begin' content structure 'end';
content: 'content' '{' newspaper news* '}';
structure: 'structure' '{' .*? '}';

newspaper: 'newspaper' '{' title date? '}';
news: NEWSNAME '{' title image? abs image? text? author? source? date?'}';

title: 'title' ':' STRING { System.out.println("Title"); };
date: 'date' ':' STRING { System.out.println("Date"); };
source: 'source' ':' STRING;
author: 'author' ':' STRING;
text: 'text' ':' STRING;
image: 'image' ':' STRING;
abs: 'abstract' ':' STRING;

STRING: '"' .*? '"';
LETTER: [a-zA-Z];
DIGIT: [0-9];
NEWSNAME: LETTER (LETTER | DIGIT | '_')*;
WS: [ \t\n]+ -> skip; // skip spaces, tabs, newlines
