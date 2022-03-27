class RegisterResponse {
  RegisterResponse({
    required this.name,
    required this.lastName,
    required this.email,
    required this.role,
  });
  late final String name;
  late final String lastName;
  late final String email;
  late final String role;

  RegisterResponse.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    lastName = json['lastName'];
    email = json['email'];
    role = json['role'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['name'] = name;
    _data['lastName'] = lastName;
    _data['email'] = email;
    _data['role'] = role;
    return _data;
  }
}
