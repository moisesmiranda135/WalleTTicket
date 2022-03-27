class LoginResponse {
  LoginResponse({
    required this.email,
    required this.name,
    required this.lastName,
    required this.role,
    required this.token,
  });
  late final String email;
  late final String name;
  late final String lastName;
  late final String role;
  late final String token;

  LoginResponse.fromJson(Map<String, dynamic> json) {
    email = json['email'];
    name = json['name'];
    lastName = json['lastName'];
    role = json['role'];
    token = json['token'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['email'] = email;
    _data['name'] = name;
    _data['lastName'] = lastName;
    _data['role'] = role;
    _data['token'] = token;
    return _data;
  }
}
