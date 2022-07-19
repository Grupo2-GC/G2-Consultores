import styled from 'styled-components';

export const ContenedorHeader = styled.div`
  background: #FFFFFF;
  border-bottom: 1px solid #ccc;
`
export const ContenedorBotones = styled.div`

    padding: 20px 50px;
    display: flex;
    /* cursor: pointer; */
    gap: 20px;
    justify-content: space-between;
    @media screen and (max-width: 1500px) {
        padding: 20px 40px; 
    }
`
export const Boton = styled.div`
    padding: 12px;  
    text-align: center; 
    font-size: 20px;
    border-radius: 50%;
    color: ${props => props.color==="azul" ? '#3963c5' : '#000'};
    background-color: #f5f6fa;
    box-shadow: 2px 4px 2px 1px rgba(0,0,0,0.1);
    cursor: pointer;
    &:hover{
      background: #57b8ff;
      color: #fff;
    }
    @media screen and (max-width: 1500px) {
        padding: 15px; 
    }
`
export const UserStyle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #0438af;
  border-radius: 10px;
  color: #fff;
  text-align: center;
  span{
    padding: 10px 30px;
  }
`
export const BoxIcon = styled.div`
  display: flex;
    gap: 20px; 
`
export const Box1 = styled.div`
    position: relative;
`
export const Tooltip = styled.div`
    width: 180px;
    text-align: center;
    position:absolute;
    bottom: 0px;
    right: 0px;
    background: #fff;
    font-size: 20px;
    padding: 10px 18px;
    border-radius: 25px;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
    opacity: 0;
    pointer-events: none;
    transition: all 0.4s cubic-bezier(0.68, -0.55,0.265,1.55);

    ${Boton}:hover & {
      opacity: 1;
      pointer-events: auto;
      bottom: -70px;
      color: blue;
      font-weight: bold;
      position: absolute;
      
    }
    ${Boton} &:before{
      position: absolute;
      content: "";
      height: 15px;
      width: 15px;
      background: #fff;
      top: -8px;
      right: -5px;
      transform: translateX(-50%) rotate(-45deg);
    }
    @media screen and (max-width: 1500px) {
      right: 0px;
      font-size: 15px;
    }
`