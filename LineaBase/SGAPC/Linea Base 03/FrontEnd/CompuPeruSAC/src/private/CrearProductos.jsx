import React, { useContext, useEffect, useState } from 'react'
import ModalConfirmaion from '../components/ModalConfirmaion'
import { ProductoContext } from "../context/productos/ProductoProvider";
import { ErrorFormulario } from "../components/ErrorFormulario";
import { ErrorFormLogin, Formulario, Box1, TitleForm, Hr, GridForm, GrupoForm, LabelForm, InputForm, SelectForm, BotonForm } from "../styles/FormularioRegistrar"
import { ContenedorModulo, TitlePage } from '../styles/UsablesStyle'

const CrearProducto = () => {
    const { agregarProducto,resetForm_producto,errorForm_producto,resetearErroresProducto } = useContext(ProductoContext);

    const [errorColaborador, setErrorColaborador] = useState(false)
    const [errorMensaje, setErrorMensaje] = useState('')
    const [modal, setModal] = useState(false);
    const [colaborador, setColaborador] = useState({
        nombre: "",
        precioVenta: "",
        precioCompra: "",
        stock: "",
        categoria: "",
        imgURL: "",
        nombreProveedor: "",
        numeroProveedor: "",
        emailProveedor: "",
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
        emailProveedor } = colaborador;
    const handleChange = (e) => {
        setColaborador({
            ...colaborador,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
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
                resetearErroresProducto();
            }, 4000);
            return;
        }
        if (errorForm_producto) {
            setErrorColaborador(true);
            // setErrorMensaje(errorFormulario);
            setTimeout(() => {
                setErrorColaborador(false);
                resetearErroresProducto();
            }, 4000);
            return;
        }
        console.log(colaborador);
        agregarProducto(colaborador)
    }
    useEffect(() => {
        if (resetForm_producto) {
            setModal(true)
            resetearErroresProducto()
            setColaborador({
                nombre: "",
                precioVenta: "",
                precioCompra: "",
                stock: "",
                categoria: "",
                imgURL: "",
                nombreProveedor: "",
                numeroProveedor: "",
                emailProveedor: "",
            })
        }
    }, [resetForm_producto]);
    return (
        <>
            <ModalConfirmaion modal={modal} setModal={setModal} producto={true} />
            <ContenedorModulo>
                <TitlePage tabla="tabla">
                    <h1>Crear Productos</h1>
                    <h4>Gestión Productos</h4>
                </TitlePage>
                <br />
                <Formulario autoComplete="off" >
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
                                    value="CREAR PRODUCTO"
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
                        {errorForm_producto ? <ErrorFormulario text="Error" /> : null}
                    </Box1>
                </Formulario>
            </ContenedorModulo>
        </>
    )
}

export default CrearProducto