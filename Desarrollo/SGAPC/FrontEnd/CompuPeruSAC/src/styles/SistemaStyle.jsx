import { motion } from 'framer-motion';
import styled from 'styled-components';

export const ContenedorSistema = styled(motion.div)`
position: relative;
width: 100%;
background-image: linear-gradient(to bottom, #0058ff, #797eff, #ada7ff, #d8d2ff, #ffffff);
`
export const BoxSistema = styled.div`
  display: grid;
  height: 100vh;
`
export const SidebarStyle = styled.div`
  position: absolute;
  z-index: 99999;
  padding-top: 30px;
  height: 100%;
  background: #0438af;
  width: 268px;
  left: ${props => props.activar ? '-268px' : '0'};
  transition: all 1s ease-in-out;
  @media screen and (max-width: 800px) {
        width: 100%;
        height: 100vh;
        position: absolute;
        left: ${props => props.activar ? '0' : '-100%'};
    }
  ul{
    display: block;
    color:#ffffff;
    text-align: center;
    text-decoration: none;
  }
  img{
    display: block;
    margin: 0 auto;
    height: 45px;
    object-fit: cover;
  }
`
export const Paginas = styled.div`
  width: 100%;
  margin-left: ${props => props.activar ? '0' : '268px'};
  background: #F5F6FA;
  height: 100vh;
  transition: all 1s ease-in-out;
  width: auto;
  @media screen and (max-width: 800px){
        margin-left: 0;
    }
`
export const ContenedorPages = styled.div`
  background: #F5F6FA;
  padding: 30px 50px;
  /* min-width: 950px; */
  @media screen and (max-width: 1500px){
    padding: 30px 30px;
  }
`
export const BoxLogo = styled.div`
    @media screen and (max-width: 800px){
        display: flex;
        padding: 0px 30px;
        justify-content: space-between;
    }
`
export const BtnMobile = styled.div`
    display: none;
    @media screen and (max-width: 800px){
        display: flex;
    }
`