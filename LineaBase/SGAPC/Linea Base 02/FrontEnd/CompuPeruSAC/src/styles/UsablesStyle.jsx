import { Link } from "react-router-dom";
import styled from "styled-components";

export const TitlePage = styled.div`
    padding-left: 10px;
    padding-bottom: ${props => props.tabla ? "0px" : "50px"};
    h1{
        font-size: 26px;
        font-weight: bold;
        line-height: 2;
        @media screen and (max-width: 600px) {
            font-size: 20px;
        }
    }
    h4{
        font-size: 15px;
        font-weight: medium;
        color: #3257db;
    }
`
export const ContenedorModulo = styled.div`
    width: 100%;
`
export const TablaContenedor = styled.div`
`
export const TablaSinDatos = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 70px;
    font-size: 26px;
    color: #0438af;
    div{
        text-align:center;
    }
`