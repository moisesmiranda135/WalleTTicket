import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthLoginDto } from 'src/app/core/entity/login/authLogin.dto';
import { AuthService } from 'src/app/core/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginDto = new AuthLoginDto();

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  doLogin() {
    this.authService.login(this.loginDto).subscribe(loginResult => {
      if (loginResult.role == "USER") {
        Swal.fire({
          icon: 'error',
          title: 'Error de Autentificación',
          text: 'El email o la contraseña no son correctos',
        })
      }else {
        this.router.navigate(['home']);
        this.authService.setLocalRequestToken(loginResult.token);
        this.authService.setRole(loginResult.role);
      }
    }, error => {
      console.log(error)
      Swal.fire({
        icon: 'error',
        title: 'Error de Autentificación',
        text: 'El email o la contraseña no son correctos',
      })
    });
  }

}
