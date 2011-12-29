

String StringParam(String id) {
  String value = String.valueOf(params.get(id));
  if(value == null) {
    println("Parameter '" + id + "' is undefined!");
    exit();
  }
  return value;
}

float floatParam(String id) {
  String value = String.valueOf(params.get(id));
  if(value.equals("null")) {
    println("Parameter '" + id + "' is undefined!");
    exit();
  }
  return Float.parseFloat(value);
}
