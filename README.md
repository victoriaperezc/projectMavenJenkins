# ServletCalculator

This web service acts as a calculator for string expressions. 
String grouping and unary minus are allowed in expressions.
The following operators are possible in expressions:
+ is addition, - is subtraction ,* is multiplication, / is division, ^ is exponentiation.

The calculation results are saved in the database. 
Execution of queries to obtain statistics on calculations is available.

The following options are possible:

COUNT(%date%) for example COUNT(16-02-2021) - number of calculations per date

OPERATION(%operation%) for example OPERATION(+)- number of calculations with operation

ONDATE(% date%) for example ONDATE(16-02-2021) - list of calculations for a date

ONOPERATION(%operation%) for example ONOPERATION(*) - calculation list with operation

POPULAR() for example POPULAR() - most used number
