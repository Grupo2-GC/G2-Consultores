import React, { useState } from 'react'
import { clienteAxios } from '../config/axios';
import { BotonForm, Box1, ErrorFormLogin, Formulario, GridForm, GrupoForm, Hr, InputForm, LabelForm, SelectForm, TitleForm } from '../styles/FormularioRegistrar';
import { CerrarModalStyle, ContenedorModal, Overlay } from '../styles/ModalStyle';
import { ErrorFormulario } from './ErrorFormulario';

const ModalEditProducto = ({ modal, setModal, item }) => {
    const [errorColaborador, setErrorColaborador] = useState(false)
    const [errorMensaje, setErrorMensaje] = useState('')
    const [colaborador, setColaborador] = useState({
        nombre: item.nombre,
        precioVenta: item.precioVenta,
        precioCompra: item.precioCompra,
        stock: item.stock,
        categoria: item.categoria,
        imgURL: item.imgURL,
        nombreProveedor: item.nombreProveedor,
        numeroProveedor: item.numeroProveedor,
        emailProveedor: item.emailProveedor,
    });
    const {
        nombre,
        precioVenta,
        precioCompra,
        stock,
        categoria,
        imgURL,
        nombreProveedor,
        numeroProveedor,
        emailProveedor,
     } = colaborador;

    const handleChange = (e) => {
        setColaborador({
            ...colaborador,
            [e.target.name]: e.target.value,
        });
    }
    const handleSubmit = async(e) => {
        e.preventDefault();
        if ([nombre,
            precioVenta,
            precioCompra,
            stock,
            categoria,
            imgURL,
            nombreProveedor,
            numeroProveedor,
            emailProveedor,].includes("")) {
            setErrorColaborador(true);
            setErrorMensaje("Todos los campos son obligatorios");
            setTimeout(() => {
                setErrorColaborador(false);
            }, 4000);
            return;
        }
        if (errorColaborador) {
            setErrorColaborador(true);
            // setErrorMensaje(errorFormulario);
            setTimeout(() => {
                setErrorColaborador(false);
            }, 4000);
            return;
        }
        // console.log(colaborador);
        await clienteAxios.put(
            `/productos/${item._id}`, colaborador
        );
        setModal(false);
    }
        const CerrarModal = () => {
            setModal(false);
        }
        // console.log(item);
        return (
            <>
                {modal && (
                    <Overlay>
                        <ContenedorModal>
                            <CerrarModalStyle onClick={CerrarModal} >X</CerrarModalStyle>
                            <Formulario autoComplete="off">
                                <Box1>
                                    <TitleForm>Informacion del Producto</TitleForm>
                                    <br />
                                    <Hr />
                                    <br />
                                    <GridForm>
                                        {/* Nombres */}
                                        <GrupoForm>
                                            <LabelForm htmlFor="nombre">
                                                Nombres
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="text"
                                                    name="nombre"
                                                    id="nombre"
                                                    value={nombre}
                                                    onChange={handleChange}
                                                />
                                            </div>
                                        </GrupoForm>
                                        {/* Precio de Venta */}
                                        <GrupoForm>
                                            <LabelForm htmlFor="precioVenta">
                                                Precio de Ventas
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="number"
                                                    name="precioVenta"
                                                    id="precioVenta"
                                                    value={precioVenta}
                                                    onChange={handleChange}
                                                />
                                            </div>
                                        </GrupoForm>
                                        {/* Precio de Compra */}
                                        <GrupoForm>
                                            <LabelForm htmlFor="precioCompra">
                                                Precio de Compra
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="number"
                                                    name="precioCompra"
                                                    id="precioCompra"
                                                    value={precioCompra}
                                                    onChange={handleChange}
                                                />
                                            </div>
                                        </GrupoForm>
                                        {/* Stock */}
                                        <GrupoForm>
                                            <LabelForm htmlFor="stock">
                                                Stock
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="number"
                                                    name="stock"
                                                    id="stock"
                                                    value={stock}
                                                    onChange={handleChange}
                                                />
                                            </div>
                                        </GrupoForm>
                                        {/* Categoria */}
                                        <GrupoForm>
                                            <LabelForm htmlFor="categoria">
                                                Categoria
                                            </LabelForm>
                                            <div>
                                                <SelectForm
                                                    name="categoria"
                                                    id="categoria"
                                                    value={categoria}
                                                    onChange={handleChange}
                                                >
                                                    <option value="">Elegir una opción</option>
                                                    <option value="Monitores">Monitores</option>
                                                    <option value="Perifericos">Perifericos</option>
                                                    <option value="CPU">CPU</option>
                                                </SelectForm>
                                            </div>
                                        </GrupoForm>
                                        {/* Imagen*/}
                                        <GrupoForm>
                                            <LabelForm htmlFor="imgURL">
                                                Imagen
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="text"
                                                    name="imgURL"
                                                    id="imgURL"
                                                    value={imgURL}
                                                    onChange={handleChange}
                                                />
                                            </div>
                                        </GrupoForm>
                                    </GridForm>
                                </Box1>
                                <Box1>
                                    <TitleForm>Informacion del Proveedor</TitleForm>
                                    <br />
                                    <Hr />
                                    <br />
                                    <GridForm>
                                        {/* Nombre del proveedor*/}
                                        <GrupoForm>
                                            <LabelForm htmlFor="nombreProveedor">
                                                Nombre del Proveedor
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="text"
                                                    name="nombreProveedor"
                                                    id="nombreProveedor"
                                                    value={nombreProveedor}
                                                    onChange={handleChange}
                                                />
                                            </div>
                                        </GrupoForm>
                                        {/* Correo del proveedor*/}
                                        <GrupoForm>
                                            <LabelForm htmlFor="emailProveedor">
                                                Correo del Proveedor
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="text"
                                                    name="emailProveedor"
                                                    id="emailProveedor"
                                                    value={emailProveedor}
                                                    onChange={handleChange}
                                                />
                                            </div>
                                        </GrupoForm>
                                        {/* Telefono */}
                                        <GrupoForm>
                                            <LabelForm htmlFor="numeroProveedor">
                                                Telfono
                                            </LabelForm>
                                            <div>
                                                <InputForm
                                                    type="number"
                                                    name="numeroProveedor"
                                                    id="numeroProveedor"
                                                    value={numeroProveedor}
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
                                                value="ACTUALIZAR PRODUCTO"
                                                className="formulario__btn"
                                                onClick={handleSubmit}
                                            />
                                        </BotonForm>
                                    </GridForm>
                                    {errorColaborador ? (
                                        <ErrorFormLogin>
                                            <h3><i className="fas fa-exclamation-circle" aria-hidden="true"></i>&nbsp;&nbsp;{errorMensaje} </h3>
                                        </ErrorFormLogin>
                                    ) : null}
                                    {errorColaborador ? <ErrorFormulario text="Error" /> : null}
                                </Box1>
                            </Formulario>
                        </ContenedorModal>
                    </Overlay>
                )}
            </>
        )
    }

    export default ModalEditProducto
