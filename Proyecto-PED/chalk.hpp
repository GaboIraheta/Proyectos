#include <iostream>

/*

    ░█████╗░██╗░░██╗░█████╗░██╗░░░░░██╗░░██╗
    ██╔══██╗██║░░██║██╔══██╗██║░░░░░██║░██╔╝
    ██║░░╚═╝███████║███████║██║░░░░░█████═╝░
    ██║░░██╗██╔══██║██╔══██║██║░░░░░██╔═██╗░
    ╚█████╔╝██║░░██║██║░░██║███████╗██║░╚██╗
    ░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝
    
    Version 0.0.1 b
*/

/*
    id = 0 -> gray
    id = 1 -> red
    id = 2 -> green
    id = 3 -> yellow
    id = 4 -> blue
    id = 5 -> magenta
    id = 6 -> cyan
    id = 7 -> white
*/

std::string Color(int id)
{
    std::string color = "\033[";
    color.append("3");
    color.append(std::to_string(id));
    color.append("m");
    return color;
}

std::string Back(int id) //id = 0 -> black
{
    std::string color = "\033[";
    color.append("4");
    color.append(std::to_string(id));
    color.append("m");
    return color;
}

std::string Light(int id)
{
    std::string color = "\033[";
    color.append("9");
    color.append(std::to_string(id));
    color.append("m");
    return color;
}

std::string LightBack(int id)
{
    std::string color = "\033[";
    color.append("10");
    color.append(std::to_string(id));
    color.append("m");
    return color;
}

std::string reset = "\033[0m\033[39m"; //default
std::string bold = "\033[1m";
std::string vanished = "\033[2m";
std::string italic = "\033[3m";
std::string underline = "\033[4m";
std::string inverted = "\033[7m";
std::string blocked = "\033[8m"; //no se ve nada
std::string strikethrough = "\033[9m";
std::string doubleline = "\033[21m";