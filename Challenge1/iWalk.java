file:///C:/Users/USER/Desktop/2019/client/locksmither/lib/main.dartÞimport 'package:flutter/material.dart';
import 'package:locksmither/routes.dart';

void main() => runApp(LockSmitherApp());

class LockSmitherApp extends StatelessWidget {
  @override

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'iWalk-LockSmither App',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
      ),
      routes: routes,
    );
  }
} )+*0
3
('%
Mfile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/models/AuthURL.dartclass AuthURL {
  String _url;
  
  AuthURL(this._url);

  AuthURL.map(dynamic obj) {
    this._url = obj["AuthURL"];
  }

  String get url => _url;

  Map<String, dynamic> toMap() {
    var map = new Map<String, dynamic>();
    map["Url"] = _url;

    return map;
  }
} !"+Kfile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/models/token.dart‚>class Token {
  String _lockURL;
  bool _isValid;
  int  _time;
  
  Token(this._lockURL, this._isValid, this._time);

  Token.map(dynamic obj) {
    this._lockURL = obj["LockURL"];
    this._isValid = obj["IsValid"];
    this._time = obj["Time"];
  }

  String get lockURL => _lockURL;
  bool get isValid => _isValid;
  int get time => _time != null ? _time : 0;

  Map<String, dynamic> toMap() {
    var map = new Map<String, dynamic>();
    map["LockURL"] = _lockURL;
    map["IsValid"] = _isValid;
    map["Time"] = _time;

    return map;
  }
} 4%%#!."+  Qfile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/network/cookie_jar.dartIimport 'package:locksmither/models/token.dart';

class CookieJar {
  static CookieJar _instance = new CookieJar.internal();
  CookieJar.internal();
  factory CookieJar() => _instance;

  Token _token;

  void setToken(Token token) {
    this._token = token;
  }

  Token getToken() {
    return this._token;
  }
} 1:% Vfile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/network/network_actions.dart„ÿimport 'dart:async';

import 'dart:convert';
import 'package:locksmither/network/network_wrapper.dart';
import 'package:locksmither/models/token.dart';
import 'package:locksmither/models/AuthURL.dart';

class NetworkActions {
  NetworkWrapper _netUtil = new NetworkWrapper();
  static const BASE_URL = "http://35.246.158.51:8070";
  static const LOGIN_URL = BASE_URL + "/auth/getUrl";

  Future<Token> login(String seed, String password) {
    var headers = new Map<String,String>();
      return _netUtil.get(LOGIN_URL, headers:headers).then((dynamic authUrl) {
      try {
        if (authUrl == null) {
          return Future<Token>.sync(() => new Token("", false, 0));
        }
        var loginUrl = BASE_URL + AuthURL.map(json.decode(authUrl.body)).url;
        Map<String,String> body = { "Seed": seed, "Password": password };
        Map<String,String> headers = {"content-type": "application/json"};
        return _netUtil.post(loginUrl,body: json.encode(body), headers:headers).then((dynamic token) {                
                return Token.map(token);
              });
      } catch (e) {
        return Future<Token>.sync(() => new Token("", false, 0));
      }
      }).catchError((e) { 
        return null; 
      });
  }
}" <133877-P
 EOKLx*C	Vfile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/network/network_wrapper.dart‡Éimport 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;

class NetworkWrapper {
  // next three lines makes this class a Singleton
  static const USER_AGENT = "iWalk-v2";
  static NetworkWrapper _instance = new NetworkWrapper.internal();
  NetworkWrapper.internal();
  factory NetworkWrapper() => _instance;

  final JsonDecoder _decoder = new JsonDecoder();

  void addUserAgent(Map headers) {
    if (!headers.containsKey("User-Agent")) {
      headers["User-Agent"] = USER_AGENT;
    }
  }

  Future<dynamic> getObject(String url, {Map headers}) {
    addUserAgent(headers);
    try {
      return http.get(url, headers: headers).then((http.Response response) {
        final String res = response.body;
        final int statusCode = response.statusCode;

        if (statusCode < 200 || statusCode > 400 || json == null) {
          return null;
        }
        return _decoder.convert(res);
      }).catchError((e) { return null; });
    } catch (e) {
      return null;
    }
  }
  
  Future<http.Response> get(String url, {Map<String,String> headers}) {
    addUserAgent(headers);
    try {
      return http.get(url, headers: headers).then((http.Response response) {
        return response;
      }).catchError((e) { return null; });
    } catch (e) {
      return null;
    }
  }

  Future<dynamic> post(String url, {Map<String,String> headers, body, encoding}) {
    addUserAgent(headers);
    try {
      return http
          .post(url, body: body, headers: headers, encoding: encoding)
          .then((http.Response response) {
            final String res = response.body;
            final int statusCode = response.statusCode;

            if (statusCode < 200 || statusCode > 400 || json == null) {
              return null;
            }
            return _decoder.convert(res);
          }).catchError((e) { return null; });
    } catch (e) {
      return null;
    }
  }
}C *4)D*3$/+:N+5E',IN,TH,/9I+0Nfile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/pages/home_page.dartƒGimport 'package:flutter/material.dart';
import 'package:locksmither/network/cookie_jar.dart';
import 'package:locksmither/models/token.dart';

class HomePage extends StatefulWidget {
   @override
  State<StatefulWidget> createState() {
    return new HomePageState();
  }
}

class HomePageState  extends State<HomePage> {
  Token _token;

  HomePageState() {
    CookieJar jar = new CookieJar();
    _token = jar.getToken();
  }

  String get lockURL => _token.lockURL;
  int get time => _token.time;


  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(title: new Text("Home"),),
      body: new Center(
        
        child: new Text("Success!\nLock Url: $lockURL\nObtained in: $time nanoseconds"
                ),
              ),
        );
  }

}% )71))!0&) 
(5
XOfile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/pages/login_page.dartñimport 'dart:ui';
import 'package:flutter/material.dart';
import 'package:locksmither/network/network_actions.dart';
import 'package:locksmither/network/cookie_jar.dart';
import 'package:locksmither/models/token.dart';

class LoginPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new LoginPageState();
  }
}

class LoginPageState extends State<LoginPage> {
  BuildContext _ctx;

  bool _isLoading = false;
  final formKey = new GlobalKey<FormState>();
  final scaffoldKey = new GlobalKey<ScaffoldState>();
  String _password;
  String _seed;

  NetworkActions _networkActions = new NetworkActions();

  LoginPageState();

  void _submit() async {
    final form = formKey.currentState;

    if (form.validate()) {
      setState(() => _isLoading = true);
      form.save();

      _networkActions.login(_seed, _password)
                      .then((result) => _loginCompleted(result))
                      .catchError((e) { 
                        _loginCompleted(new Token("", false, 0));
                      });
    }
  }

  void _loginCompleted(Token result) async {
    Duration dur = new Duration(microseconds: (result.time/1000).round());
    _showSnackBar("Completed in: " +  dur.inMilliseconds.toString() + " milliseconds result is: " + result.isValid.toString());
    setState(() => _isLoading = false);
    if (result != null && result.isValid) {
      CookieJar jar = new CookieJar();
      jar.setToken(result);
      Navigator.of(_ctx).pushReplacementNamed("/home");
    } 
  }


  void _showSnackBar(String text) {
    scaffoldKey.currentState
        .showSnackBar(new SnackBar(content: new Text(text)));
  }

  @override
  Widget build(BuildContext context) {
    _ctx = context;
    var loginBtn = new RaisedButton(
      onPressed: _submit,
      child: new Text("LOGIN"),
      color: Colors.blue,
    );
    var loginForm = new Column(
      children: <Widget>[
        new Text(
          "iWalk-LockSmither App",
          textScaleFactor: 2.0,
        ),
        new Form(
          key: formKey,
          child: new Column(
            children: <Widget>[
              new Padding(
                padding: const EdgeInsets.all(8.0),
                child: new TextFormField(
                  onSaved: (val) => _seed = val,
                  decoration: new InputDecoration(labelText: "Seed"),
                ),
              ),
              new Padding(
                padding: const EdgeInsets.all(8.0),
                child: new TextFormField(
                  onSaved: (val) => _password = val,
                  decoration: new InputDecoration(labelText: "Password"),
                ),
              ),
            ],
          ),
        ),
        _isLoading ? new CircularProgressIndicator() : loginBtn
      ],
      crossAxisAlignment: CrossAxisAlignment.center,
    );

    return new Scaffold(
      appBar: null,
      key: scaffoldKey,
      body: new Container(
        child: new Center(
          child: new ClipRect(
            child: new BackdropFilter(
              filter: new ImageFilter.blur(sigmaX: 10.0, sigmaY: 10.0),
              child: new Container(
                child: loginForm,
                height: 300.0,
                width: 300.0,
                decoration: new BoxDecoration(
                    color: Colors.grey.shade200.withOpacity(0.5)),
              ),
            ),
          ),
        ),
      ),
    );
  }
}y )<71*
)"1/7:(*/B*C.L€)-(9%?
(&!!$!!5+2G5+6KA
6 (I%# 0D
Efile:///C:/Users/USER/Desktop/2019/client/locksmither/lib/routes.dartcimport 'package:flutter/material.dart';
import 'package:locksmither/pages/login_page.dart';
import 'package:locksmither/pages/home_page.dart';

final routes = {
  '/login':         (BuildContext context) => new LoginPage(),
  '/home':         (BuildContext context) => new HomePage(),
  '/' :          (BuildContext context) => new LoginPage(),
};