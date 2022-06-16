class TicketResponse {
  TicketResponse({
    required this.id,
    required this.title,
    required this.description,
    required this.price,
    required this.dateInit,
    required this.dateEnd,
    required this.ticketImage,
    required this.productImage,
    required this.isFavorite,
    required this.categoryName,
    required this.categoryIcon,
    required this.companyName,
    required this.companyImage,
    required this.userName,
    required this.userLastName,
    required this.userEmail,
  });
  late final int id;
  late final String title;
  late final String description;
  late final double price;
  late final String dateInit;
  late final String dateEnd;
  late final String ticketImage;
  late final String productImage;
  late final bool isFavorite;
  late final String categoryName;
  late final String categoryIcon;
  late final String companyName;
  late final String companyImage;
  late final String userName;
  late final String userLastName;
  late final String userEmail;

  TicketResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    title = json['title'];
    description = json['description'];
    price = json['price'];
    dateInit = json['dateInit'];
    dateEnd = json['dateEnd'];
    ticketImage = json['ticketImage'];
    productImage = json['productImage'];
    isFavorite = json['isFavorite'];
    categoryName = json['categoryName'];
    categoryIcon = json['categoryIcon'];
    companyName = json['companyName'];
    companyImage = json['companyImage'];
    userName = json['userName'];
    userLastName = json['userLastName'];
    userEmail = json['userEmail'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['title'] = title;
    _data['description'] = description;
    _data['price'] = price;
    _data['dateInit'] = dateInit;
    _data['dateEnd'] = dateEnd;
    _data['ticketImage'] = ticketImage;
    _data['productImage'] = productImage;
    _data['isFavorite'] = isFavorite;
    _data['categoryName'] = categoryName;
    _data['categoryIcon'] = categoryIcon;
    _data['companyName'] = companyName;
    _data['companyImage'] = companyImage;
    _data['userName'] = userName;
    _data['userLastName'] = userLastName;
    _data['userEmail'] = userEmail;
    return _data;
  }
}
