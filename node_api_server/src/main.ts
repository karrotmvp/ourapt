import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { listeningPort } from './app.env';
import { setupSwagger } from './docs/swagger';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  setupSwagger(app);
  await app.listen(listeningPort);
}

bootstrap();
