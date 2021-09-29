import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { listeningPort } from './app.env';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  await app.listen(listeningPort);
}
bootstrap();
