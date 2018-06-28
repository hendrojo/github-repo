<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Convert Number">
		<meta name="author" content="Hendro">
		<script src="/resources/js/jquery.js"></script>
		<title>Converting Number</title>
		
		<style>
			@import url("/resources/css/bootstrap.css");
			table,td {border: 1px solid black;}
		</style>
	</head>

	<body>
		<form id="convertData" action="/index" method="post">
			<div class="container-fluid">
				<table class="table">
					<tr>
						<td class="align-middle">
							Input : <textarea rows="10" style="width:85%" id="input"></textarea>
						</td>
						<td class="align-middle">
							<button type="button" class="btn" onclick="doConvert();">
								Add Input Data
							</button>
						</td>
						<td class="align-middle">
							Output : <textarea rows="10" style="width:85%" id="output" disabled></textarea>
						</td>
					</tr>
				</table>
			</div>
			<div class="row-fluid span12">
				<div class="row-fluid span2">
					<table class="table">
						<thead>
							<tr>
								<td colspan="2">Roman Symbol</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${RomanSymbol}" var="listRoman">
								<tr>
									<td><c:out value="${listRoman.key}"/></td>
									<td><c:out value="${listRoman.value}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="row-fluid span5">		
					<table class="table">
						<thead>
							<tr>
								<td colspan="2">Alien Symbol</td>
							</tr>
						</thead>
						<tbody id="tbody_alien">
							<c:forEach items="${AlienSymbol}" var="listAlien">
								<tr>
									<td><c:out value="${listAlien.key}"/></td>
									<td><c:out value="${listAlien.value}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="row-fluid span3">
					<table class="table">
						<thead>
							<tr>
								<td colspan="2">Metal Credits</td>
							</tr>
						</thead>
						<tbody id="tbody_metal">
							<c:forEach items="${MetalCredits}" var="listMetal">
								<tr>
									<td><c:out value="${listMetal.key}"/></td>
									<td><c:out value="${listMetal.value}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</form>
		
		<script>
			function doConvert() {
				var blnSuccess = false;
				$.ajax({
					async : false,
					url : "/convert_text",
					data : {
						data : $("#input").val()
					},
					success : function(data) {				
						$('#output').val(data);
						blnSuccess = true;
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) { 
	                    alert("Status: " + textStatus + "\n" + "Error: " + errorThrown);
	                }  
				});
				if (blnSuccess) {
					$.ajax({
						async : false,
						url : "/get_alien_symbol",
						success : function(data) {
							if (data!="") {
								$('#tbody_alien').html(data);
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) { 
		                    alert("Status: " + textStatus + "\n" + "Error: " + errorThrown);
		                }  
					});

					$.ajax({
						async : false,
						url : "/get_metal_credits",
						success : function(data) {
							if (data!="") {			
								$('#tbody_metal').html(data);
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) { 
		                    alert("Status: " + textStatus + "\n" + "Error: " + errorThrown);
		                }  
					});
				}
			}
		</script>
	</body>
</html>