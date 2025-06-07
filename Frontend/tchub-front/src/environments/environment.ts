export const environment = {
    production: false,

    baseUrlApi: 'http://localhost:8080/tchub',

    idiomas: {
    'EN': 'English',
    'DE': 'German',
    'ES': 'Spanish',
    'FR': 'French',
    'IT': 'Italian',
    'JA': 'Japanese',
    'PT-BR': 'Portuguese (Brazil)',
    'RU': 'Russian',
    'ZH-CN': 'Chinese Simplified'
    } as Record<string, string> ,

    idiomasEs: {
    'EN': 'Inglés',
    'DE': 'Alemán',
    'ES': 'Español',
    'FR': 'Francés',
    'IT': 'Italiano',
    'JA': 'Japonés',
    'PT-BR': 'Portugues (Brasil)',
    'RU': 'Ruso',
    'ZH-CN': 'Chino Simplificado'
    } as Record<string, string> ,
    
    backCardImg: {
        'MTG': "C:\Users\ymism\Desktop\TCHub\Frontend\tchub-front\public\back\magicBack.jpeg"
    } as Record<string, string> 
};
