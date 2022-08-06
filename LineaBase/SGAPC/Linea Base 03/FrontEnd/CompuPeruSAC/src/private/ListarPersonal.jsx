import React from 'react'
import { ContenedorModulo, TablaContenedor, TitlePage } from '../styles/UsablesStyle'
import { TablaUsuarios } from '../tables/TablaUsuarios'

const ListarPersonal = () => {
  return (
    <>
      <ContenedorModulo>
        <TitlePage tabla="tabla">
          <h1>Listado de Personal</h1>
          <h4>Gestión Personal</h4>
        </TitlePage>
        <br />
        <TablaContenedor>
          <TablaUsuarios />
        </TablaContenedor>
      </ContenedorModulo>
    </>
  )
}

export default ListarPersonal