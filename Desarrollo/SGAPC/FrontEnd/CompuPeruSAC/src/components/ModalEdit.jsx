import React, { useContext, useEffect, useState } from "react";
import { ColaboradorContext } from "../context/colaboradores/ColaboradorProvider";
import { Overlay, ContenedorModal, CerrarModalStyle } from "../styles/ModalStyle";
import {Formulario,Box1,TitleForm,GridForm,GrupoForm,LabelForm,InputForm,SelectForm,BotonForm, Hr } from "../styles/FormularioRegistrar"
const ModalEdit = ({modal,setModal}) => {
    const {resetColaboradorActual,idColaborador,colaboradores,actualizarColaborador} = useContext(ColaboradorContext)
    
   useEffect(() => {
     const existeId = colaboradores.find(item => item._id === idColaborador)
      if (existeId) {
        setColaborador(existeId)
      }
   },[idColaborador,colaboradores])


    const [colaborador, setColaborador] = useState({
      first_name: "",
      last_name:"",
      dni:"",
      gender:"",
      birthday:"",
      age:"",
      email:"",
      phone:"",
      enterprise:"",
      headquarter:"",
      area:"",
      profile:"",
      admission_date:"",
      departure_date:""
    });  
    const { 
      first_name,
      last_name,
      dni,
      gender,
      birthday,
      age,
      email,
      phone,
      enterprise,
      headquarter,
      area,
      profile,
      admission_date,
      departure_date } = colaborador;
    
      
    const handleChange = (e) => {
      setColaborador({
        ...colaborador,
        [e.target.name]: e.target.value,
      });
        

      };
      const handleSubmit = (e) => {
        e.preventDefault();
        setColaborador({
          ...colaborador,
          [e.target.name]: e.target.value,
        });
        actualizarColaborador(colaborador);
        setModal(false);
        // navigate('listado')
      }

    const CerrarModal = () => {
        setModal(false);
        resetColaboradorActual();
        
    }
  return (
    <>
       {modal && (<Overlay>
        <ContenedorModal>
        <CerrarModalStyle onClick={CerrarModal} >X</CerrarModalStyle>
        <Formulario autoComplete="off" >
        <Box1>
          <TitleForm>Informacion del Colaborador</TitleForm>
          <br />
          <Hr />
          <GridForm>
            {/* Nombres */}
            <GrupoForm>
              <LabelForm htmlFor="first_name">
                Nombres
              </LabelForm>
              <div>
                <InputForm
                  type="text"
                  name="first_name"
                  id="first_name"
                  value={first_name}
                  onChange={handleChange}
                />
              </div>
            </GrupoForm>
            {/* Apellidos */}
            <GrupoForm>
              <LabelForm htmlFor="last_name">
                Apellidos
              </LabelForm>
              <div>
                <InputForm
                  type="text"
                  name="last_name"
                  id="last_name"
                  value={last_name}
                  onChange={handleChange}
                />
              </div>
            </GrupoForm>
            {/* DNI */}
            <GrupoForm>
              <LabelForm htmlFor="dni">
                DNI
              </LabelForm>
              <div>
                <InputForm
                  type="number"
                  name="dni"
                  id="dni"
                  value={dni}
                  onChange={handleChange}
                />
              </div>
            </GrupoForm>
            {/* Genero */}
            <GrupoForm>
              <LabelForm htmlFor="gender">
                Género
              </LabelForm>
              <div>
                <SelectForm
                  name="gender"
                  id="gender"
                  value={gender}
                  onChange={handleChange}
                >
                  <option value="">Elegir una opción</option>
                  <option value="hombre">Hombre</option>
                  <option value="mujer">Mujer</option>
                </SelectForm>
              </div>
            </GrupoForm>
            {/* Fecha de nacimiento */}
            <GrupoForm>
                    <LabelForm htmlFor="birthday">
                      Fecha de nacimiento
                    </LabelForm>
                    <div>
                      <InputForm
                        type="date"
                        name="birthday"
                        id="birthday"
                        value={birthday}
                        onChange={handleChange}
                      />
                    </div>
                  </GrupoForm>
            {/* Edad */}
            <GrupoForm>
            <LabelForm htmlFor="age">
                      Edad
                    </LabelForm>
                    <div>
                      <InputForm
                        type="number"
                        name="age"
                        id="age"
                        value={age}
                        onChange={handleChange}
                      />
                    </div>
            </GrupoForm>
            {/* Correo electronico */}
            <GrupoForm>
            <LabelForm htmlFor="email">
                      Correo electrónico
                    </LabelForm>
                    <div>
                      <InputForm
                        type="email"
                        name="email"
                        id="email"
                        value={email}
                        onChange={handleChange}
                      />
                    </div>
            </GrupoForm>
            {/* Telefono */}
            <GrupoForm>
            <LabelForm htmlFor="phone">
                      Telefono
                    </LabelForm>
                    <div>
                      <InputForm
                        type="number"
                        name="phone"
                        id="phone"
                        value={phone}
                        onChange={handleChange}
                      />
                    </div>
            </GrupoForm>
          </GridForm>
        </Box1>
        <Box1>
          <TitleForm>Informacion de la empresa</TitleForm>
          <br />
          <Hr />
          <GridForm>
            {/* Empresa */}
            <GrupoForm>
              <LabelForm htmlFor="enterprise">
                Empresa
              </LabelForm>
              <div>
                <SelectForm
                  name="enterprise"
                  id="enterprise"
                  value={enterprise}
                  onChange={handleChange}
                >
                  <option value="">Elegir una opción</option>
                  <option value="compuPeru">CompuPeru</option>
                </SelectForm>
              </div>
            </GrupoForm>
            {/* Sede */}
            <GrupoForm>
              <LabelForm htmlFor="headquarter">
                Sede
              </LabelForm>
              <div>
                <SelectForm
                  name="headquarter"
                  id="headquarter"
                  value={headquarter}
                  onChange={handleChange}
                >
                  <option value="">Elegir una opción</option>
                  <option value="SanMiguel">San Migel</option>
                  <option value="Callao">Callao</option>
                  <option value="SJL">SJL</option>
                </SelectForm>
              </div>
            </GrupoForm>
            {/* Area */}
            <GrupoForm>
              <LabelForm htmlFor="area">
                Área
              </LabelForm>
              <div>
                <SelectForm
                  name="area"
                  id="area"
                  value={area}
                  onChange={handleChange}
                >
                  <option value="">Elegir una opción</option>
                  <option value="Ventas">Ventas</option>
                  <option value="Mantenimiento">Mantenimiento</option>
                  <option value="Administrador">Administrador</option>
                  <option value="Almacen">Almacen</option>
                </SelectForm>
              </div>
            </GrupoForm>

            {/* Perfil del colaborador */}
            <GrupoForm>
              <LabelForm htmlFor="profile">
                Perfil del colaborador
              </LabelForm>
              <div>
                <SelectForm
                  name="profile"
                  id="profile"
                  value={profile}
                  onChange={handleChange}
                >
                  <option value="">Elegir una opción</option>
                  <option value="Empleado">Empleado</option>
                  <option value="Logistica">Logistica</option>
                  <option value="Almacenero">Almacenero</option>
                </SelectForm>
              </div>
            </GrupoForm>
            {/* Fecha de Ingreso */}
            <GrupoForm>
              <LabelForm htmlFor="admission_date">
                Fecha de Ingreso
              </LabelForm>
              <div>
                <InputForm
                  type="date"
                  name="admission_date"
                  id="admission_date"
                  value={admission_date}
                  onChange={handleChange}
                />
              </div>
            </GrupoForm>
            {/* Fecha de Salida */}
            <GrupoForm>
              <LabelForm htmlFor="departure_date">
                Fecha de Salida
              </LabelForm>
              <div>
                <InputForm
                  type="date"
                  name="departure_date"
                  id="departure_date"
                  value={departure_date}
                  onChange={handleChange}
                />
              </div>
            </GrupoForm>
            {/* boton */}
            <BotonForm>
              <LabelForm htmlFor="salida">
                &nbsp;&nbsp;&nbsp;&nbsp;
              </LabelForm>
              <InputForm
                type="submit"
                value="GUARDAR CAMBIOS"
                className="formulario__btn"
                onClick={handleSubmit}
              />
            </BotonForm>
          </GridForm>
        </Box1>
      </Formulario>
          
        </ContenedorModal>
      </Overlay>)}
    </>
  )
}

export default ModalEdit
