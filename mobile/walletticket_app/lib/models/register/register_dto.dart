class RegisterDto {
  RegisterDto({
    required this.name,
    required this.lastName,
    required this.email,
    required this.password,
  });
  late final String name;
  late final String lastName;
  late final String email;
  late final String password;

  RegisterDto.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    lastName = json['lastName'];
    email = json['email'];
    password = json['password'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['name'] = name;
    _data['lastName'] = lastName;
    _data['email'] = email;
    _data['password'] = password;
    return _data;
  }
}
