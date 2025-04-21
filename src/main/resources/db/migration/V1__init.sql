CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS pg_uuidv7;

CREATE TABLE "user"
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255),
    phone            VARCHAR(255),
    email            VARCHAR(255),
    profile_image_id BIGINT,
    status           VARCHAR(50),
    created_at       TIMESTAMP DEFAULT NOW(),
    updated_at       TIMESTAMP DEFAULT NOW(),
    deleted_at       TIMESTAMP DEFAULT NULL
);
CREATE UNIQUE INDEX "uidx_user_email" ON "user" (email);
COMMENT ON TABLE "user" IS 'User table to store user information';
COMMENT ON COLUMN "user".id IS 'Primary key for user table';
COMMENT ON COLUMN "user".name IS 'Name of the user';
COMMENT ON COLUMN "user".phone IS 'Phone number of the user';
COMMENT ON COLUMN "user".email IS 'Email address of the user';
COMMENT ON COLUMN "user".profile_image_id IS 'Foreign key to the media table for profile image';
COMMENT ON COLUMN "user".status IS 'Status of the user (e.g., active, inactive)';
COMMENT ON COLUMN "user".created_at IS 'Timestamp when the user was created';
COMMENT ON COLUMN "user".updated_at IS 'Timestamp when the user was last updated';
COMMENT ON COLUMN "user".deleted_at IS 'Timestamp when the user was deleted';

CREATE TABLE workspace
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    used_storage      INTEGER      NOT NULL,
    used_storage_unit VARCHAR(50)  NOT NULL,
    max_user_count    INTEGER      NOT NULL,
    max_storage       INTEGER      NOT NULL,
    max_storage_unit  VARCHAR(50)  NOT NULL,
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW(),
    deleted_at        TIMESTAMP DEFAULT NULL
);
COMMENT ON TABLE workspace IS 'Workspace table to store workspace information';
COMMENT ON COLUMN workspace.id IS 'Primary key for workspace table';
COMMENT ON COLUMN workspace.name IS 'Name of the workspace';
COMMENT ON COLUMN workspace.created_at IS 'Timestamp when the workspace was created';
COMMENT ON COLUMN workspace.updated_at IS 'Timestamp when the workspace was last updated';
COMMENT ON COLUMN workspace.deleted_at IS 'Timestamp when the workspace was deleted';

CREATE TABLE user_workspace
(
    user_id      BIGINT NOT NULL,
    workspace_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, workspace_id)
);
COMMENT ON TABLE user_workspace IS 'Workspace user table to store user-workspace relationships';
COMMENT ON COLUMN user_workspace.user_id IS 'Foreign key to the user table for the user';
COMMENT ON COLUMN user_workspace.workspace_id IS 'Foreign key to the workspace table for the workspace';


CREATE TABLE "authentication_provider"
(
    id     INTEGER PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    type   VARCHAR(50)  NOT NULL,
    config JSONB
);
COMMENT ON TABLE "authentication_provider" IS 'Authentication provider table to store authentication methods';
COMMENT ON COLUMN "authentication_provider".id IS 'Primary key for authentication provider table';
COMMENT ON COLUMN "authentication_provider".name IS 'Name of the authentication provider';
COMMENT ON COLUMN "authentication_provider".type IS 'Type of authentication provider (e.g., OAuth, SAML)';
COMMENT ON COLUMN "authentication_provider".config IS 'Configuration for the authentication provider (e.g., API keys, settings)';

INSERT INTO "authentication_provider" (id, name, type, config)
VALUES (1, 'GOOGLE', 'OAuth2', null),
       (2, 'KAKAO', 'OAuth2', null),
       (3, 'NAVER', 'OAuth2', null);


CREATE TABLE "authentication"
(
    id                         BIGSERIAL PRIMARY KEY,
    authentication_provider_id INT  NOT NULL,
    provider_user_id           TEXT NOT NULL,
    provider_user_name         TEXT,
    provider_user_email        VARCHAR(255),
    password                   VARCHAR(255),
    access_token               VARCHAR(1024),
    access_token_expires_at    TIMESTAMP,
    refresh_token              VARCHAR(1024),
    refresh_token_expires_at   TIMESTAMP,
    login_at                   TIMESTAMP DEFAULT NOW(),
    login_ip                   VARCHAR(50),
    login_user_agent           VARCHAR(255),
    last_login_at              TIMESTAMP DEFAULT NOW(),
    last_login_ip              VARCHAR(50),
    last_login_user_agent      VARCHAR(255),
    created_at                 TIMESTAMP DEFAULT NOW(),
    updated_at                 TIMESTAMP DEFAULT NOW()
);
CREATE UNIQUE INDEX "uidx_authentication_provider_user_id" ON "authentication" (authentication_provider_id, provider_user_id);
COMMENT ON TABLE "authentication" IS 'User authentication table to store user authentication information';
COMMENT ON COLUMN "authentication".id IS 'Primary key for user authentication table';
COMMENT ON COLUMN "authentication".authentication_provider_id IS 'Foreign key to the authentication provider table for the authentication method';
COMMENT ON COLUMN "authentication".provider_user_id IS 'User ID from the authentication provider';
COMMENT ON COLUMN "authentication".provider_user_name IS 'User name from the authentication provider';
COMMENT ON COLUMN "authentication".provider_user_email IS 'User email from the authentication provider';
COMMENT ON COLUMN "authentication".password IS 'Password for the user';
COMMENT ON COLUMN "authentication".access_token IS 'Access token for the user';
COMMENT ON COLUMN "authentication".refresh_token IS 'Refresh token for the user';
COMMENT ON COLUMN "authentication".created_at IS 'Timestamp when the authentication was created';
COMMENT ON COLUMN "authentication".updated_at IS 'Timestamp when the authentication was last updated';

CREATE TABLE "user_authentication"
(
    user_id           BIGINT NOT NULL,
    authentication_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, authentication_id)
);
COMMENT ON TABLE "user_authentication" IS 'User authentication table to store user-authentication relationships';
COMMENT ON COLUMN "user_authentication".user_id IS 'Foreign key to the user table for the user';
COMMENT ON COLUMN "user_authentication".authentication_id IS 'Foreign key to the authentication table for the authentication method';

CREATE TABLE "media"
(
    id           uuid PRIMARY KEY DEFAULT uuid_generate_v7(),
    workspace_id BIGINT       NOT NULL,
    url          VARCHAR(255) NOT NULL,
    file_name    TEXT         NOT NULL,
    type         VARCHAR(100) NOT NULL,
    size         BIGINT       NOT NULL,
    size_unit    VARCHAR(50)  NOT NULL,
    created_at   TIMESTAMP        DEFAULT NOW(),
    created_by   BIGINT,
    updated_at   TIMESTAMP        DEFAULT NOW(),
    updated_by   BIGINT,
    deleted_at   TIMESTAMP        DEFAULT NULL,
    deleted_by   BIGINT
);
CREATE INDEX "idx_media_workspace_id" ON "media" (workspace_id);
COMMENT ON TABLE "media" IS 'Media table to store media files';
COMMENT ON COLUMN "media".id IS 'Primary key for media table';
COMMENT ON COLUMN "media".url IS 'URL of the media file';
COMMENT ON COLUMN "media".file_name IS 'File name of the media file';
COMMENT ON COLUMN "media".type IS 'Type of the media file (e.g., image, video)';
COMMENT ON COLUMN "media".size IS 'Size of the media file';
COMMENT ON COLUMN "media".size_unit IS 'Unit of size (e.g., bytes, KB, MB)';
COMMENT ON COLUMN "media".created_at IS 'Timestamp when the media was created';
COMMENT ON COLUMN "media".created_by IS 'Foreign key to the user table for the creator of the media';
COMMENT ON COLUMN "media".updated_at IS 'Timestamp when the media was last updated';
COMMENT ON COLUMN "media".updated_by IS 'Foreign key to the user table for the last updater of the media';
COMMENT ON COLUMN "media".deleted_at IS 'Timestamp when the media was deleted';
COMMENT ON COLUMN "media".deleted_by IS 'Foreign key to the user table for the deleter of the media';

CREATE TABLE "media_history"
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v7(),
    action     VARCHAR(50) NOT NULL,
    user_id    BIGINT      NOT NULL,
    media_id   uuid        NOT NULL,
    created_at TIMESTAMP        DEFAULT NOW()
);
COMMENT ON TABLE "media_history" IS 'Media history table to track media actions';
COMMENT ON COLUMN "media_history".id IS 'Primary key for media history table';
COMMENT ON COLUMN "media_history".action IS 'Action performed on the media (e.g., upload, delete)';
COMMENT ON COLUMN "media_history".user_id IS 'Foreign key to the user table for the user who performed the action';
COMMENT ON COLUMN "media_history".media_id IS 'Foreign key to the media table for the media item';
COMMENT ON COLUMN "media_history".created_at IS 'Timestamp when the action was performed';

CREATE TABLE "organization"
(
    id               BIGSERIAL PRIMARY KEY,
    workspace_id     BIGINT,
    name             VARCHAR(255) NOT NULL,
    phone            VARCHAR(255),
    email            VARCHAR(255),
    profile_image_id BIGINT,
    created_at       TIMESTAMP DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMP DEFAULT NOW(),
    updated_by       BIGINT,
    deleted_at       TIMESTAMP DEFAULT NULL,
    deleted_by       BIGINT
);
CREATE UNIQUE INDEX "uidx_organization_email" ON "organization" (email);
COMMENT ON TABLE "organization" IS 'Organization table to store organization information';
COMMENT ON COLUMN "organization".id IS 'Primary key for organization table';
COMMENT ON COLUMN "organization".workspace_id IS 'Foreign key to the workspace table for the organization';
COMMENT ON COLUMN "organization".name IS 'Name of the organization';
COMMENT ON COLUMN "organization".phone IS 'Phone number of the organization';
COMMENT ON COLUMN "organization".email IS 'Email address of the organization';
COMMENT ON COLUMN "organization".profile_image_id IS 'Foreign key to the media table for organization profile image';
COMMENT ON COLUMN "organization".created_at IS 'Timestamp when the organization was created';
COMMENT ON COLUMN "organization".created_by IS 'Foreign key to the user table for the creator of the organization';
COMMENT ON COLUMN "organization".updated_at IS 'Timestamp when the organization was last updated';
COMMENT ON COLUMN "organization".updated_by IS 'Foreign key to the user table for the last updater of the organization';
COMMENT ON COLUMN "organization".deleted_at IS 'Timestamp when the organization was deleted';
COMMENT ON COLUMN "organization".deleted_by IS 'Foreign key to the user table for the deleter of the organization';

CREATE TABLE "organization_hierarchy"
(
    ancestor_id   BIGINT NOT NULL,
    descendant_id BIGINT NOT NULL,
    workspace_id  BIGINT NOT NULL,
    depth         INT    NOT NULL,
    PRIMARY KEY (ancestor_id, descendant_id, workspace_id)
);
COMMENT ON TABLE "organization_hierarchy" IS 'Closure table for organization hierarchy';
COMMENT ON COLUMN "organization_hierarchy".ancestor_id IS 'Ancestor organization ID';
COMMENT ON COLUMN "organization_hierarchy".descendant_id IS 'Descendant organization ID';
COMMENT ON COLUMN "organization_hierarchy".workspace_id IS 'Foreign key to the workspace table for the workspace';
COMMENT ON COLUMN "organization_hierarchy".depth IS 'Depth of the descendant in the hierarchy';

CREATE TABLE "user_organization"
(
    user_id         BIGINT NOT NULL,
    organization_id BIGINT NOT NULL,
    created_at      TIMESTAMP DEFAULT NOW(),
    created_by      BIGINT,
    updated_at      TIMESTAMP DEFAULT NOW(),
    updated_by      BIGINT,
    PRIMARY KEY (user_id, organization_id)
);
COMMENT ON TABLE "user_organization" IS 'User organization table to store user-organization relationships';
COMMENT ON COLUMN "user_organization".user_id IS 'Foreign key to the user table for the user';
COMMENT ON COLUMN "user_organization".organization_id IS 'Foreign key to the organization table for the organization';
COMMENT ON COLUMN "user_organization".created_at IS 'Timestamp when the relationship was created';
COMMENT ON COLUMN "user_organization".created_by IS 'Foreign key to the user table for the creator of the relationship';
COMMENT ON COLUMN "user_organization".updated_at IS 'Timestamp when the relationship was last updated';
COMMENT ON COLUMN "user_organization".updated_by IS 'Foreign key to the user table for the last updater of the relationship';


CREATE TABLE product
(
    id                     BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(255)   NOT NULL,
    description            TEXT,
    price_per_people       DECIMAL(10, 2) NOT NULL,
    subscription_period    INT            NOT NULL,
    subscription_unit      VARCHAR(50)    NOT NULL,
    workspace_storage      INTEGER        NOT NULL,
    workspace_storage_unit VARCHAR(50)    NOT NULL,
    max_user_count         INTEGER        NOT NULL,
    created_at             TIMESTAMP DEFAULT NOW(),
    created_by             BIGINT,
    updated_at             TIMESTAMP DEFAULT NOW(),
    updated_by             BIGINT,
    deleted_at             TIMESTAMP DEFAULT NULL,
    deleted_by             BIGINT
);
COMMENT ON TABLE product IS 'Product table to store product information';
COMMENT ON COLUMN product.id IS 'Primary key for product table';
COMMENT ON COLUMN product.name IS 'Name of the product';
COMMENT ON COLUMN product.description IS 'Description of the product';
COMMENT ON COLUMN product.price_per_people IS 'Price per person for the product';
COMMENT ON COLUMN product.created_at IS 'Timestamp when the product was created';
COMMENT ON COLUMN product.created_by IS 'Foreign key to the user table for the creator of the product';
COMMENT ON COLUMN product.updated_at IS 'Timestamp when the product was last updated';
COMMENT ON COLUMN product.updated_by IS 'Foreign key to the user table for the last updater of the product';
COMMENT ON COLUMN product.deleted_at IS 'Timestamp when the product was deleted';
COMMENT ON COLUMN product.deleted_by IS 'Foreign key to the user table for the deleter of the product';



CREATE TABLE payment_method
(
    id                 BIGSERIAL PRIMARY KEY,
    user_id            BIGINT      NOT NULL,
    method_type        VARCHAR(50) NOT NULL,
    payment_gateway_id INTEGER     NOT NULL,
    payment_key        JSONB       NOT NULL,
    is_default         BOOLEAN     DEFAULT TRUE,
    status             VARCHAR(50) DEFAULT 'active',
    created_at         TIMESTAMP   DEFAULT NOW(),
    created_by         BIGINT,
    updated_at         TIMESTAMP   DEFAULT NOW(),
    updated_by         BIGINT,
    deleted_at         TIMESTAMP   DEFAULT NULL,
    deleted_by         BIGINT
);
CREATE INDEX "idx_payment_method_user_id" ON payment_method (user_id);
COMMENT ON TABLE payment_method IS 'Payment method table to store user payment methods';
COMMENT ON COLUMN payment_method.id IS 'Primary key for payment method table';
COMMENT ON COLUMN payment_method.user_id IS 'Foreign key to the user table for the user';
COMMENT ON COLUMN payment_method.method_type IS 'Type of payment method (e.g., credit card, bank transfer)';
COMMENT ON COLUMN payment_method.payment_gateway_id IS 'Foreign key to the payment gateway table for the payment method';
COMMENT ON COLUMN payment_method.payment_key IS 'Payment key for the payment method';
COMMENT ON COLUMN payment_method.is_default IS 'Indicates if the payment method is the default method for the user';
COMMENT ON COLUMN payment_method.status IS 'Status of the payment method (e.g., active, inactive)';
COMMENT ON COLUMN payment_method.created_at IS 'Timestamp when the payment method was created';
COMMENT ON COLUMN payment_method.created_by IS 'Foreign key to the user table for the creator of the payment method';
COMMENT ON COLUMN payment_method.updated_at IS 'Timestamp when the payment method was last updated';
COMMENT ON COLUMN payment_method.updated_by IS 'Foreign key to the user table for the last updater of the payment method';
COMMENT ON COLUMN payment_method.deleted_at IS 'Timestamp when the payment method was deleted';
COMMENT ON COLUMN payment_method.deleted_by IS 'Foreign key to the user table for the deleter of the payment method';

CREATE TABLE payment_gateway
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    type   VARCHAR(50)  NOT NULL,
    config JSONB        NOT NULL
);
COMMENT ON TABLE payment_gateway IS 'Payment gateway table to store payment gateway information';
COMMENT ON COLUMN payment_gateway.id IS 'Primary key for payment gateway table';
COMMENT ON COLUMN payment_gateway.name IS 'Name of the payment gateway';
COMMENT ON COLUMN payment_gateway.type IS 'Type of payment gateway (e.g., TossPayment, ..)';
COMMENT ON COLUMN payment_gateway.config IS 'Configuration for the payment gateway (e.g., API keys, settings)';

CREATE TABLE subscription_info
(
    id                BIGSERIAL PRIMARY KEY,
    user_id           BIGINT      NOT NULL,
    product_id        BIGINT      NOT NULL,
    workspace_id      BIGINT      NOT NULL,
    status            VARCHAR(50) NOT NULL,
    next_billing_date TIMESTAMP,
    retry_attempts    INT       DEFAULT 0,
    last_billing_date TIMESTAMP,
    created_at        TIMESTAMP DEFAULT NOW(),
    created_by        BIGINT,
    updated_at        TIMESTAMP DEFAULT NOW(),
    updated_by        BIGINT,
    deleted_at        TIMESTAMP DEFAULT NULL,
    deleted_by        BIGINT
);
COMMENT ON TABLE subscription_info IS 'Subscription info table to store user subscription information';
COMMENT ON COLUMN subscription_info.id IS 'Primary key for subscription info table';
COMMENT ON COLUMN subscription_info.user_id IS 'Foreign key to the user table for the user';
COMMENT ON COLUMN subscription_info.product_id IS 'Foreign key to the product table for the product';
COMMENT ON COLUMN subscription_info.workspace_id IS 'Foreign key to the workspace table for the workspace';
COMMENT ON COLUMN subscription_info.status IS 'Status of the subscription (e.g., active, inactive)';
COMMENT ON COLUMN subscription_info.next_billing_date IS 'Next billing date for the subscription';
COMMENT ON COLUMN subscription_info.retry_attempts IS 'Number of retry attempts for billing';
COMMENT ON COLUMN subscription_info.last_billing_date IS 'Last billing date for the subscription';
COMMENT ON COLUMN subscription_info.created_at IS 'Timestamp when the subscription was created';
COMMENT ON COLUMN subscription_info.created_by IS 'Foreign key to the user table for the creator of the subscription';
COMMENT ON COLUMN subscription_info.updated_at IS 'Timestamp when the subscription was last updated';
COMMENT ON COLUMN subscription_info.updated_by IS 'Foreign key to the user table for the last updater of the subscription';
COMMENT ON COLUMN subscription_info.deleted_at IS 'Timestamp when the subscription was deleted';
COMMENT ON COLUMN subscription_info.deleted_by IS 'Foreign key to the user table for the deleter of the subscription';


CREATE TABLE payment_history
(
    id                 uuid PRIMARY KEY DEFAULT uuid_generate_v7(),
    subscription_id    BIGINT         NOT NULL,
    payment_method_id  BIGINT         NOT NULL,
    amount             DECIMAL(10, 2) NOT NULL,
    balance            DECIMAL(10, 2) NOT NULL,
    cancel_transaction BOOLEAN          DEFAULT FALSE,
    status             VARCHAR(50)    NOT NULL,
    transaction_id     VARCHAR(255),
    transaction_date   TIMESTAMP        DEFAULT NOW(),
    details            JSONB,
    created_at         TIMESTAMP        DEFAULT NOW()
);
COMMENT ON TABLE payment_history IS 'Payment history table to store payment transaction history';
COMMENT ON COLUMN payment_history.id IS 'Primary key for payment history table';
COMMENT ON COLUMN payment_history.subscription_id IS 'Foreign key to the subscription info table for the subscription';
COMMENT ON COLUMN payment_history.payment_method_id IS 'Foreign key to the payment method table for the payment method';
COMMENT ON COLUMN payment_history.amount IS 'Amount of the payment';
COMMENT ON COLUMN payment_history.balance IS 'Balance after the payment';
COMMENT ON COLUMN payment_history.cancel_transaction IS 'Indicates if the transaction was canceled';
COMMENT ON COLUMN payment_history.status IS 'Status of the payment (e.g., success, failed)';
COMMENT ON COLUMN payment_history.transaction_id IS 'Transaction ID for the payment';
COMMENT ON COLUMN payment_history.transaction_date IS 'Date of the payment transaction';
COMMENT ON COLUMN payment_history.details IS 'Additional details for the payment transaction';
COMMENT ON COLUMN payment_history.created_at IS 'Timestamp when the payment transaction was created';


CREATE TABLE document
(
    id                 uuid PRIMARY KEY DEFAULT uuid_generate_v7(),
    workspace_id       BIGINT       NOT NULL,
    name               VARCHAR(255) NOT NULL,
    type               VARCHAR(50)  NOT NULL,
    size               BIGINT       NOT NULL,
    size_unit          VARCHAR(50)  NOT NULL,
    data               JSONB        NOT NULL,
    directory_id       uuid         NOT NULL,
    organization_id    BIGINT,
    organization_read  BOOLEAN          DEFAULT FALSE,
    organization_write BOOLEAN          DEFAULT FALSE,
    role_id            BIGINT,
    role_read          BOOLEAN          DEFAULT FALSE,
    role_write         BOOLEAN          DEFAULT FALSE,
    created_at         TIMESTAMP        DEFAULT NOW(),
    created_by         BIGINT,
    updated_at         TIMESTAMP        DEFAULT NOW(),
    updated_by         BIGINT,
    deleted_at         TIMESTAMP        DEFAULT NULL,
    deleted_by         BIGINT
);
CREATE INDEX "idx_document_workspace_id_directory_id" ON document (workspace_id);
CREATE INDEX "idx_document_directory_id" ON document (directory_id);
COMMENT ON TABLE document IS 'Document table to store document information';
COMMENT ON COLUMN document.id IS 'Primary key for document table';
COMMENT ON COLUMN document.workspace_id IS 'Foreign key to the workspace table for the workspace';
COMMENT ON COLUMN document.name IS 'Name of the document';
COMMENT ON COLUMN document.type IS 'Type of the document (e.g., text, image)';
COMMENT ON COLUMN document.size IS 'Size of the document';
COMMENT ON COLUMN document.size_unit IS 'Unit of size (e.g., bytes, KB, MB)';
COMMENT ON COLUMN document.data IS 'Data of the document in JSON format';
COMMENT ON COLUMN document.directory_id IS 'Foreign key to the directory table for the document';
COMMENT ON COLUMN document.created_at IS 'Timestamp when the document was created';
COMMENT ON COLUMN document.created_by IS 'Foreign key to the user table for the creator of the document';
COMMENT ON COLUMN document.updated_at IS 'Timestamp when the document was last updated';
COMMENT ON COLUMN document.updated_by IS 'Foreign key to the user table for the last updater of the document';
COMMENT ON COLUMN document.deleted_at IS 'Timestamp when the document was deleted';
COMMENT ON COLUMN document.deleted_by IS 'Foreign key to the user table for the deleter of the document';

CREATE TABLE directory
(
    id                 uuid PRIMARY KEY DEFAULT uuid_generate_v7(),
    workspace_id       BIGINT       NOT NULL,
    name               VARCHAR(255) NOT NULL,
    organization_id    BIGINT,
    organization_read  BOOLEAN          DEFAULT FALSE,
    organization_write BOOLEAN          DEFAULT FALSE,
    role_id            BIGINT,
    role_read          BOOLEAN          DEFAULT FALSE,
    role_write         BOOLEAN          DEFAULT FALSE,
    created_at         TIMESTAMP        DEFAULT NOW(),
    created_by         BIGINT,
    updated_at         TIMESTAMP        DEFAULT NOW(),
    updated_by         BIGINT,
    deleted_at         TIMESTAMP        DEFAULT NULL,
    deleted_by         BIGINT
);
CREATE INDEX "idx_directory_workspace_id" ON directory (workspace_id);
COMMENT ON TABLE directory IS 'Directory table to store directory information';
COMMENT ON COLUMN directory.id IS 'Primary key for directory table';
COMMENT ON COLUMN directory.workspace_id IS 'Foreign key to the workspace table for the workspace';
COMMENT ON COLUMN directory.name IS 'Name of the directory';
COMMENT ON COLUMN directory.organization_id IS 'Foreign key to the organization table for the organization';
COMMENT ON COLUMN directory.organization_read IS 'Indicates if the organization has read access to the directory';
COMMENT ON COLUMN directory.organization_write IS 'Indicates if the organization has write access to the directory';
COMMENT ON COLUMN directory.role_id IS 'Foreign key to the role table for the role';
COMMENT ON COLUMN directory.role_read IS 'Indicates if the role has read access to the directory';
COMMENT ON COLUMN directory.role_write IS 'Indicates if the role has write access to the directory';
COMMENT ON COLUMN directory.created_at IS 'Timestamp when the directory was created';
COMMENT ON COLUMN directory.created_by IS 'Foreign key to the user table for the creator of the directory';
COMMENT ON COLUMN directory.updated_at IS 'Timestamp when the directory was last updated';
COMMENT ON COLUMN directory.updated_by IS 'Foreign key to the user table for the last updater of the directory';
COMMENT ON COLUMN directory.deleted_at IS 'Timestamp when the directory was deleted';
COMMENT ON COLUMN directory.deleted_by IS 'Foreign key to the user table for the deleter of the directory';

CREATE TABLE directory_hierarchy
(
    ancestor_id   uuid   NOT NULL,
    descendant_id uuid   NOT NULL,
    workspace_id  BIGINT NOT NULL,
    depth         INT    NOT NULL,
    PRIMARY KEY (ancestor_id, descendant_id, workspace_id)
);
COMMENT ON TABLE directory_hierarchy IS 'Closure table for document hierarchy';
COMMENT ON COLUMN directory_hierarchy.ancestor_id IS 'Ancestor document ID';
COMMENT ON COLUMN directory_hierarchy.descendant_id IS 'Descendant document ID';
COMMENT ON COLUMN directory_hierarchy.depth IS 'Depth of the descendant in the hierarchy';


CREATE TABLE role
(
    id           BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    created_at   TIMESTAMP DEFAULT NOW(),
    created_by   BIGINT,
    updated_at   TIMESTAMP DEFAULT NOW(),
    updated_by   BIGINT,
    deleted_at   TIMESTAMP DEFAULT NULL,
    deleted_by   BIGINT
);
CREATE UNIQUE INDEX "uidx_role_workspace_id_name" ON role (workspace_id, name);
COMMENT ON TABLE role IS 'Role table to store role information';
COMMENT ON COLUMN role.id IS 'Primary key for role table';
COMMENT ON COLUMN role.workspace_id IS 'Foreign key to the workspace table for the role';
COMMENT ON COLUMN role.name IS 'Name of the role';
COMMENT ON COLUMN role.description IS 'Description of the role';
COMMENT ON COLUMN role.created_at IS 'Timestamp when the role was created';
COMMENT ON COLUMN role.created_by IS 'Foreign key to the user table for the creator of the role';
COMMENT ON COLUMN role.updated_at IS 'Timestamp when the role was last updated';
COMMENT ON COLUMN role.updated_by IS 'Foreign key to the user table for the last updater of the role';
COMMENT ON COLUMN role.deleted_at IS 'Timestamp when the role was deleted';
COMMENT ON COLUMN role.deleted_by IS 'Foreign key to the user table for the deleter of the role';

CREATE TABLE user_role
(
    user_id      BIGINT NOT NULL,
    workspace_id BIGINT NOT NULL,
    role_id      BIGINT NOT NULL,
    PRIMARY KEY (user_id, workspace_id, role_id)
);
COMMENT ON TABLE user_role IS 'User role table to store user-role relationships';
COMMENT ON COLUMN user_role.user_id IS 'Foreign key to the user table for the user';
COMMENT ON COLUMN user_role.workspace_id IS 'Foreign key to the workspace table for the workspace';
COMMENT ON COLUMN user_role.role_id IS 'Foreign key to the role table for the role';


