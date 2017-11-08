function modify() {
    console.log($("#mainForm").attr("method"));
    console.log($("#mainForm").attr("action"));
    $("#mainForm").submit();
}