import React, { useContext, useEffect, useState } from 'react'
import { useLocation } from "react-router-dom";
import useScreenSize from '../hooks/useScreenSize';
import { EnlaceBox, LinkList, NavBox } from '../styles/NavegadorStyle';
import { BiDesktop } from 'react-icons/bi';
import { FaWarehouse, FaShoppingBag,FaUserLock } from 'react-icons/fa';
import { MdOutlineControlPoint } from 'react-icons/md';
import { AuthContext } from '../context/auth/AuthProvider';


const Navegador = ({ Sidebar }) => {
    const location = useLocation();
    const urlActual = location.pathname;
    const [colapsed, setColapsed] = useState(false);
    const [colapsed2, setColapsed2] = useState(false);
    const [colapsed3, setColapsed3] = useState(false);
    const [phone, setPhone] = useState(false);
    const { width } = useScreenSize();

    const { usuario } = useContext(AuthContext);
    console.log(usuario);

    useEffect(() => {
        if (width <= 800) {
            setPhone(true);
        } else {
            setPhone(false);
        }
    }, [width])

    const HandleColapsed = () => {
        setColapsed(!colapsed);
    }
    const HandleColapsed2 = () => {
        setColapsed2(!colapsed2);
    }
    const HandleColapsed3 = () => {
        setColapsed3(!colapsed3);
    }

    return (
        <NavBox>
                <EnlaceBox first="first">
                    {phone ? <LinkList onClick={Sidebar} to=""
                        className={`${urlActual === "/sistema" ? "link__actual" : null
                            } `}> <div first="first"><div><BiDesktop /> Escritorio</div> <span>&nbsp;</span> </div> </LinkList>
                        : <LinkList to=""
                            className={`${urlActual === "/sistema" ? "link__actual" : null
                                } `}><div first="first"><div><BiDesktop /> Escritorio</div><span>&nbsp;</span></div></LinkList>}

                </EnlaceBox>
            {usuario.usuario.roles[0]==="ADMIN" &&
                <EnlaceBox >
                    <div onClick={HandleColapsed}><div><FaUserLock /> Personal</div> <MdOutlineControlPoint /></div>
                    {colapsed && <>
                        {phone ? <>
                            <LinkList onClick={Sidebar} to="addPersonal"
                                className={`${urlActual === "/sistema/addPersonal" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Crear Personal</h4></LinkList>
                            <LinkList onClick={Sidebar} to="personal"
                                className={`${urlActual === "/sistema/personal" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Listar Personal</h4></LinkList>     
                        </>
                            : <>
                            <LinkList to="addPersonal"
                                    className={`${urlActual === "/sistema/addPersonal" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Crear Personal</h4></LinkList>
                                <LinkList to="personal"
                                    className={`${urlActual === "/sistema/personal" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Listar Personal</h4></LinkList>  
                            </>}
                    </>}
                </EnlaceBox>
            }
            {usuario.usuario.roles[0]!=="LOGISTICA" &&
                <EnlaceBox >
                    <div onClick={HandleColapsed2}><div><FaWarehouse /> Almacen</div> <MdOutlineControlPoint /></div>
                    {colapsed2 && <>
                        {phone ? <>
                            <LinkList onClick={Sidebar} to="addProducto"
                                className={`${urlActual === "/sistema/addProducto" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Crear Productos</h4></LinkList>
                                    <LinkList onClick={Sidebar} to="listarproductos"
                                className={`${urlActual === "/sistema/listarproductos" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Listar Productos</h4></LinkList>
                            {/* <LinkList onClick={Sidebar} to="categorias"
                                className={`${urlActual === "/sistema/categorias" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Categorias</h4></LinkList> */}
                        </>
                            : <>
                                <LinkList to="addProducto"
                                    className={`${urlActual === "/sistema/addProducto" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Crear Productos</h4></LinkList>
                                        <LinkList to="listarproductos"
                                    className={`${urlActual === "/sistema/listarproductos" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Listar Productos</h4></LinkList>
                                {/* <LinkList to="categorias"
                                    className={`${urlActual === "/sistema/categorias" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Categorias</h4></LinkList> */}
                            </>}
                    </>}
                </EnlaceBox>
            }

            {usuario.usuario.roles[0]!=="ALMACENERO" &&
                <EnlaceBox >
                    <div onClick={HandleColapsed3}><div><FaShoppingBag /> Compras</div> <MdOutlineControlPoint /></div>
                    {colapsed3 && <>
                        {phone ? <>
                            <LinkList onClick={Sidebar} to="listarordenes"
                                className={`${urlActual === "/sistema/listarordenes" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Listado de Ordenes</h4></LinkList>
                            {/* <LinkList onClick={Sidebar} to="personal"
                                className={`${urlActual === "/sistema/personal" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Listado de Personal</h4></LinkList>      */}
                        </>
                            : <>
                            <LinkList to="listarordenes"
                                    className={`${urlActual === "/sistema/listarordenes" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Lista de Ordenes</h4></LinkList>
                                {/* <LinkList to="personal"
                                    className={`${urlActual === "/sistema/personal" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Listado de Personal</h4></LinkList>   */}
                            </>}
                    </>}
                </EnlaceBox>
            }
            {false &&
                <EnlaceBox >
                    <div onClick={HandleColapsed3}><div><FaShoppingBag /> Compras</div> <MdOutlineControlPoint /></div>
                    {colapsed3 && <>
                        {phone ? <>
                            <LinkList onClick={Sidebar} to="addPersonal"
                                className={`${urlActual === "/sistema/addPersonal" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Crear Personal</h4></LinkList>
                            <LinkList onClick={Sidebar} to="personal"
                                className={`${urlActual === "/sistema/personal" ? "link__actual" : null
                                    } `}><h4><MdOutlineControlPoint />Listar Personal</h4></LinkList>     
                        </>
                            : <>
                            <LinkList to="addPersonal"
                                    className={`${urlActual === "/sistema/addPersonal" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Crear Personal</h4></LinkList>
                                <LinkList to="personal"
                                    className={`${urlActual === "/sistema/personal" ? "link__actual" : null
                                        } `}><h4><MdOutlineControlPoint />Listar Personal</h4></LinkList>  
                            </>}
                    </>}
                </EnlaceBox>
            }

        </NavBox>
    )
}

export default Navegador