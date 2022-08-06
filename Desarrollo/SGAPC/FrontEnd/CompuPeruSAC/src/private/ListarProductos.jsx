import React from 'react'
import { ContenedorModulo, TablaContenedor, TitlePage } from '../styles/UsablesStyle'
import TablaProductos from '../tables/TablaProductos'

const ListarProductos = () => {
  return (
    <>
      <ContenedorModulo>
        <TitlePage tabla="tabla">
          <h1>Listado de Productos</h1>
          <h4>Gestión Productos</h4>
        </TitlePage>
        <br />
        <TablaContenedor>
          <TablaProductos />
        </TablaContenedor>
      </ContenedorModulo>
    </>
  )
}

export default ListarProductos