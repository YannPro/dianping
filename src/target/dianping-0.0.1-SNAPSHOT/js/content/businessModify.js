function modify() {
    $("#mainForm").attr("method", "PUT");
    console.log($("#mainForm").attr("method"));
    console.log($("#mainForm").attr("action"));
    $("#mainForm").submit();
}