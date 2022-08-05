import React, { useContext } from 'react'
import { AuthContext } from '../context/auth/AuthProvider';
import { CardContainer, CardEstado, CardInfo1, CardInfoSub, CardSala, ContainerBox, Hr, ImageEscritorio } from '../styles/Escritorio.Style';
import { ContenedorModulo, TitlePage } from '../styles/UsablesStyle';

const Estritorio = () => {
  const { usuario } = useContext(AuthContext);
  console.log(usuario.usuario);
  return (
    <>
      <ContenedorModulo>
        <TitlePage tabla="tabla">
          <h1>Estritorio</h1>
          <h4>Datos del Usuario</h4>
        </TitlePage>
        <br />
        <ContainerBox>
          <CardContainer>
            <CardSala>
            <CardEstado>
              <img src="https://th.bing.com/th/id/R.9d116570fe0344ec36296b627838d502?rik=%2bP%2f84FQ04TH5jw&pid=ImgRaw&r=0" alt="estado" />
              <CardInfo1>
                <p> Rol </p>
                {usuario && <h4>{usuario.usuario.roles[0]}</h4> } 
              </CardInfo1>
            </CardEstado>
            <CardInfoSub>
              <h4>Nombre</h4>
              <p>{usuario.usuario.nombre}</p>
            </CardInfoSub>
            <CardInfoSub>
              <h4>DNI</h4>
              <p>{usuario.usuario.dni}</p>
            </CardInfoSub>
            <Hr />
            <CardInfoSub>
              <h3>Telefono</h3>
              <p>{usuario.usuario.telefono}</p>
            </CardInfoSub>
            <CardInfoSub>
              <h3>Email</h3>
              <p>{usuario.usuario.email}</p>
            </CardInfoSub>
            </CardSala>
          </CardContainer>
          <ImageEscritorio>
            <img src="https://cdn.dribbble.com/users/257709/screenshots/17652705/media/1793c57763ac485cfbf42961445ef542.png?compress=1&resize=768x576&vertical=top" alt="" />
          </ImageEscritorio>
        </ContainerBox>
      </ContenedorModulo>
    </>
  )
}

export default Estritorio
