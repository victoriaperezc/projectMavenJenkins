<html>
<head>
        <meta http-equiv=”Content-Type” content=”text/html; charset=UTF-8″ />
        <div class="wrapper fadeInDown">
            <div id="formContent">
<title>Calculator</title>
    <link href="index.css" rel="stylesheet">

<body>

<h1 align="left">Calculadora de expresiones</h1>
<style>

    td {
        line-height: 2;
    }
</style>
<table>
    <tr>
        <td width="500" >
            <form method="POST" action="ExpressionServlet">
                <h2>Ingrese la expresion:</h2><br>
                <p style="text-align:center">
                Por ejemplo (-7*8+9-(9/4.5))^2,	<br>
                Donde + es suma, - es resta, <br>
                * es multiplicacion, / es division, ^ es exponenciacion,<br>
		        tambien puedes utilizar parentesis para agrupar operaciones.<br>
                </p>
                <br>
                <input type="text" name="expression">

                <input type="SUBMIT">
            </form>
            <%  String expression;
                if (request.getParameter("expression") != null) {
                    expression = request.getParameter("expression");
                } else { expression = ""; }
            %>
            Tu entrada: <br>
            <%= expression %>
            <br>
            <%  String answer;
                if (request.getAttribute("answer") != null) {
                    answer = (String) request.getAttribute("answer");
                } else { answer = ""; }
            %>
            Resultado: <br>
            <%= answer %>
            <br>
        </td>
    </tr>
</table>
</div>
</body>
</html>
