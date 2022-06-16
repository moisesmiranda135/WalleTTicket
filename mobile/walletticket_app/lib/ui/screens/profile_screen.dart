import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:walletticket_app/styles/styles.dart';
import 'package:walletticket_app/ui/screens/login_screen.dart';

void main() {
  runApp(const ProfileScreen());
}

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SingleChildScrollView(
      child: Container(
        color: Colors.grey[300],
        child: Center(
          child: Column(
            children: [
              Container(
                width: 360,
                height: 50,
                margin: const EdgeInsets.only(top: 5),
                child: Stack(
                  children: const [
                    Positioned(
                        top: 25,
                        left: 20,
                        child: Text(
                          "Perfil de Usuario",
                          style: TextStyle(
                              fontSize: 24, fontWeight: FontWeight.w800),
                        ))
                  ],
                ),
              ),
              Container(
                  width: 360,
                  height: 500,
                  margin: const EdgeInsets.symmetric(vertical: 20),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(25),
                  ),
                  child: Column(
                    children: [
                      Container(
                        margin: const EdgeInsets.symmetric(
                            horizontal: 30, vertical: 20),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: const [
                            Icon(Icons.edit_outlined,
                                size: 25,
                                color: WalleTTicketStyle.primaryColor),
                            Text(
                              "  Editar perfil",
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.symmetric(
                            horizontal: 30, vertical: 0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: const [
                            Icon(Icons.lock,
                                size: 25,
                                color: WalleTTicketStyle.primaryColor),
                            Text(
                              "  Cambiar contraseña",
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.only(left: 30, top: 20),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            const Icon(Icons.vpn_key,
                                size: 25,
                                color: WalleTTicketStyle.primaryColor),
                            TextButton(
                              onPressed: () => Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) =>
                                          const LoginScreen())),
                              child: const Text(
                                "  Cerrar Sesión",
                                style: TextStyle(
                                    fontSize: 16,
                                    color: Colors.black,
                                    fontWeight: FontWeight.w500),
                              ),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.only(
                            top: 30, left: 40, bottom: 10),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: const [
                            Text(
                              "Contacto",
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.symmetric(
                            horizontal: 30, vertical: 10),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: const [
                            Icon(Icons.email,
                                size: 25,
                                color: WalleTTicketStyle.primaryColor),
                            Text(
                              "  Email: contacto@walletticket.com",
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.symmetric(
                            horizontal: 30, vertical: 10),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: const [
                            Icon(FontAwesomeIcons.twitter,
                                size: 25,
                                color: WalleTTicketStyle.primaryColor),
                            Text(
                              "  Twitter: @walletticket",
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.symmetric(
                            horizontal: 30, vertical: 10),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: const [
                            Icon(FontAwesomeIcons.instagram,
                                size: 25,
                                color: WalleTTicketStyle.primaryColor),
                            Text(
                              "  Instagram: @walletticket",
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.symmetric(
                            horizontal: 30, vertical: 10),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: const [
                            Icon(FontAwesomeIcons.facebook,
                                size: 25,
                                color: WalleTTicketStyle.primaryColor),
                            Text(
                              "  Facebook : WalletTicket company",
                              style: TextStyle(
                                  fontSize: 16,
                                  color: Colors.black,
                                  fontWeight: FontWeight.w500),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.only(left: 40),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            TextButton(
                              onPressed: () => showModalBottomSheet(
                                  context: context,
                                  builder: (context) => SingleChildScrollView(
                                        child: Center(
                                          child: Column(
                                            children: [
                                              Container(
                                                margin: const EdgeInsets.only(
                                                    top: 15),
                                                child: const Text(
                                                  "Términos y condiciones de uso",
                                                  style: TextStyle(
                                                      fontSize: 24,
                                                      color: Colors.blue,
                                                      fontWeight:
                                                          FontWeight.w500),
                                                ),
                                              ),
                                              Container(
                                                margin: const EdgeInsets.only(
                                                    top: 15),
                                                width: 350,
                                                child: const Text(
                                                  "El Usuario reconoce y acepta que el uso de los contenidos y/o servicios ofrecidos por la presente aplicación móvil será bajo su exclusivo riesgo y/o responsabilidad. El Usuario se compromete a utilizar la presente aplicación móvil y todo su contenido y Servicios de conformidad con la ley, la moral, el orden público y las presentes Condiciones de Uso, y las Condiciones Particulares que, en su caso, le sean de aplicación. Así mismo, se compromete hacer un uso adecuado de los servicios y/o contenidos de la aplicación móvil y a no emplearlos para realizar actividades ilícitas o constitutivas de delito, que atenten contra los derechos de terceros y/o que infrinjan la regulación sobre propiedad intelectual e industrial, o cualesquiera otras normas del ordenamiento jurídico aplicable. En particular, el Usuario se compromete a no trasmitir, introducir, difundir y poner a disposición de terceros, cualquier tipo de material e información (datos contenidos, mensajes, dibujos, archivos de sonido e imagen, fotografías, software, etc.) que sean contrarios a la ley, la moral, el orden público y las presentes Condiciones de Uso y, en su caso, a las Condiciones Particulares que le sean de aplicación.",
                                                  textAlign: TextAlign.justify,
                                                ),
                                              )
                                            ],
                                          ),
                                        ),
                                      )),
                              child: const Text(
                                "Términos y condiciones de uso",
                                style: TextStyle(
                                    fontSize: 16,
                                    color: Colors.black,
                                    fontWeight: FontWeight.w500),
                              ),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        margin: const EdgeInsets.symmetric(horizontal: 40),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            TextButton(
                              onPressed: () => showModalBottomSheet(
                                  context: context,
                                  builder: (context) => SingleChildScrollView(
                                        child: Center(
                                          child: Column(
                                            children: [
                                              Container(
                                                margin: const EdgeInsets.only(
                                                    top: 15),
                                                child: const Text(
                                                  "Politica y Privacidad",
                                                  style: TextStyle(
                                                      fontSize: 24,
                                                      color: Colors.blue,
                                                      fontWeight:
                                                          FontWeight.w500),
                                                ),
                                              ),
                                              Container(
                                                margin: const EdgeInsets.only(
                                                    top: 15),
                                                width: 350,
                                                child: const Text(
                                                  "WalleTTicket se reserva el derecho a modificar las presentes Condiciones de Uso con el objeto de adecuarlas a la legislación vigente aplicable en cada momento. Las presentes Condiciones de Uso no excluyen la posibilidad de que determinados Servicios de las aplicaciones, por sus características particulares, sean sometidos, además de a las Condiciones Generales de Uso, a sus propias condiciones particulares de uso (en adelante las Condiciones Particulares). ACM APPS SL podrá, en cualquier momento y sin necesidad de previo aviso, realizar cambios y actualizaciones de las presentes Condiciones de Uso y de la Política de Privacidad. Estos cambios serán publicados en la Web y en la/s Aplicación/es y serán efectivos desde el momento de su publicación. Como consecuencia de lo anterior, el Usuario deberá revisar periódicamente si hay cambios en estas Condiciones y, tanto si existe consentimiento expreso como si no, si el Usuario continua usando el Servicio tras la publicación, ello implica la aceptación y asunción de los mismos. En caso de que no esté de acuerdo con las actualizaciones de las Condiciones de uso o de la Política de Privacidad, podrá renunciar dejando de usar el Servicio. El acceso y descarga de la aplicación es gratuito salvo en lo relativo al coste de la conexión a través de la red de telecomunicaciones suministrada por el proveedor de acceso contratado por los usuarios. Determinados servicios son exclusivos para nuestros clientes y su acceso se encuentra restringido.",
                                                  textAlign: TextAlign.justify,
                                                ),
                                              )
                                            ],
                                          ),
                                        ),
                                      )),
                              child: const Text(
                                "Política y privacidad",
                                style: TextStyle(
                                    fontSize: 16,
                                    color: Colors.black,
                                    fontWeight: FontWeight.w500),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  )),
            ],
          ),
        ),
      ),
    ));
  }
}
