import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import { INestApplication } from '@nestjs/common';

export function setupSwagger(app: INestApplication) {
  const documentOption = new DocumentBuilder()
    .setTitle('Ume-Apartment API Docs')
    .setVersion('1.0.0')
    .build();

  const document = SwaggerModule.createDocument(app, documentOption);
  SwaggerModule.setup('api-docs', app, document);
}
