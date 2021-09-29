import * as dotenv from 'dotenv';

dotenv.config();

export const listeningPort = process.env.LISTENING_PORT ?? 3000;
