!!! warning "Integrations can be configured only via the _YAML_ file!"

    Also, **don't forget to restart** the _Kuvasz_ container after modifying the _YAML_ file for the changes to take effect!

Your integrations are the **channels** through which
_Kuvasz_ **sends notifications** about the status of your monitors. You can use
_Kuvasz_ without any integrations, but it won't make much sense in most of the cases, because you won't be notified about any issues with your monitors.

## Where to put them exactly?

You're free to put your integrations under `integrations:` in your _YAML_ configuration file, wherever you like. The only restriction is that under `integrations` you can only use the integration types that are supported by _Kuvasz_.

## How can they be referenced?

You can reference your integrations in your monitors by their _ID_, which is always dynamically generated by **concatenating the `type` and `name` of the integration**, separated by a colon (`:`). For example, if you have a _Slack_ integration with the name `slack-example`, its ID will be **`slack:slack-example`**.

!!!tip
    
    Find out more about assigning integrations to your monitors in the [**Managing monitors**](managing-monitors.md#modifying-the-assigned-integrations) section of the documentation.

## Common settings

All the integrations **share some common, generic settings
**, which means that it doesn't matter which integration you configure, you can use the same settings for all of them.

### `name`

<!-- md:version 2.0.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The name of the integration. It must be **unique across the given type** of integration, so you can have multiple Slack integrations, for example, but they must have different names, and vice versa: you can have multiple integrations of different types with the same name.

!!! warning

    The name can contain only alphanumeric characters (`a-z`, `A-Z`, `0-9`), underscores (`_`), or dashes (`-`).

```yaml hl_lines="3"
integrations:
  pagerduty:
    - name: pd_global
      integration-key: YourOwnIntegrationKey
```

### `enabled`

<!-- md:version 2.0.0 -->
<!-- md:default `true` -->
<!-- md:type `boolean` -->

Whether the integration is enabled or not. If it's set to `false`, the integration won't be used, and **no notifications will be sent** through it, however you can still reference it in your monitors.

```yaml hl_lines="4"
integrations:
  pagerduty:
    - name: pd_global
      enabled: true
      integration-key: YourOwnIntegrationKey
```

### `global`

<!-- md:version 2.0.0 -->
<!-- md:default `false` -->
<!-- md:type `boolean` -->

Whether the integration is global or not. If it's set to `true`, the integration **will be used for all monitors by default**, even if they don't have a specific integration assigned to them. If it's set to `false`, the integration will only be used for monitors that explicitly reference it.

```yaml hl_lines="4"
integrations:
  pagerduty:
    - name: pd_global
      global: true
      integration-key: YourOwnIntegrationKey
```

## Slack

**Configuration alias**: `slack`

The Slack integration allows you to send notifications to a Slack channel **via a webhook URL**.

### `webhook-url`

<!-- md:version 2.0.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The webhook URL of the Slack channel where the notifications will be sent. You can create a webhook URL in your Slack workspace by following the [**official documentation**](https://api.slack.com/messaging/webhooks){target="_blank"}.

---

```yaml title="Slack integration example"
integrations:
  slack:
    - name: slack-example
      webhook-url: 'https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXX'
    - name: slack-global
      webhook-url: 'https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXX'
      global: true
    # ... other Slack integrations
```

## Discord

**Configuration alias**: `discord`

The Discord integration allows you to send notifications to a Discord channel **via a webhook URL**.

### `webhook-url`

<!-- md:version 2.3.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The webhook URL of the Discord channel where the notifications will be sent. You can create a webhook URL in your Discord server by following these steps:

1. Go to your Discord server settings
2. Navigate to **Integrations** → **Webhooks**
3. Click **New Webhook**
4. Configure the webhook name and select the target channel
5. Copy the **Webhook URL**

For more information, see the [**official Discord documentation**](https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks){target="_blank"}.

---

```yaml title="Discord integration example"
integrations:
  discord:
    - name: discord-example
      webhook-url: 'https://discord.com/api/webhooks/123456789/abcdef1234567890abcdef1234567890'
    - name: discord-global
      webhook-url: 'https://discord.com/api/webhooks/987654321/fedcba0987654321fedcba0987654321'
      global: true
    # ... other Discord integrations
```

## Email

**Configuration alias**: `email`

The email integration allows you to send notifications via email. You can have multiple email integrations, each with its **own sender and recipient addresses**.

!!! warning

    To make the email integration work, it's not enough to just configure the integration itself, you also **need to set up the _SMTP_ configuration** in your _YAML_ file. You can have multiple email integrations, but they will **all use the same** SMTP configuration.

    For more information, see the [**SMTP configuration**](configuration.md#smtp) section of the documentation.

### `from-address`

<!-- md:version 2.0.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The email address from which the notifications will be sent. This is the address that will **appear in the "From" field** of the email.

### `to-address`

<!-- md:version 2.0.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The email address **to which the notifications will be sent**.

---

```yaml title="Email integration example"
integrations:
  email:
    - name: email_implicitly_enabled
      from-address: noreply@kuvasz-uptime.dev
      to-address: your@email.address
    - name: email_disabled
      from-address: noreply@other-sender.com
      to-address: other-recipient@blabla.com
      enabled: false
    # ... other email integrations
```

## PagerDuty

**Configuration alias**: `pagerduty`

The _PagerDuty_ integration allows you to **trigger incidents in PagerDuty** when a monitor goes down, and to automatically resolve them when the monitor comes back up.

!!!info "Things to do in PagerDuty first"

    1. From the **Configuration** menu, select **Services**.
    2. There are two ways to add an integration to a service:
       * **If you are adding your integration to an existing service**: Click the **name** of the service you want to add the integration to. Then, select the **Integrations** tab and click the **New Integration** button.
       * **If you are creating a new service for your integration**: Please read our documentation in section [Configuring Services and Integrations](https://support.pagerduty.com/docs/services-and-integrations#section-configuring-services-and-integrations){target="_blank"} and follow the steps outlined in the [Create a New Service](https://support.pagerduty.com/docs/services-and-integrations#section-create-a-new-service){target="_blank"} section, selecting "Kuvasz" as the **Integration Type** in step 4. Continue with the "In Kuvasz"  section (below) once you have finished these steps.
    3. Enter an **Integration Name** in the format `monitoring-tool-service-name` (e.g.  Kuvasz-Your-Service) and select "Kuvasz" from the Integration Type menu.
    4. Click the **Add Integration** button to save your new integration. You will be redirected to the Integrations tab for your service.
    5. An **Integration Key** will be generated on this screen. Keep this key saved in a safe place, as it will be used when you configure the integration with Kuvasz in the next section.
      ![Copy PD key](https://pdpartner.s3.amazonaws.com/ig-template-copy-integration-key.png)

### `integration-key`

<!-- md:version 2.0.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The **integration key of the _PagerDuty_ service** where the incidents will be created. You can find this key in your _PagerDuty_ service settings.

---

```yaml title="PagerDuty integration example"
integrations:
  pagerduty:
    - name: pd_global
      integration-key: YourOwnIntegrationKey
      global: true
    - name: pd_disabled
      integration-key: YourOtherIntegrationKey
      enabled: false
    # ... other PagerDuty integrations
```

## Telegram

**Configuration alias**: `telegram`

The _Telegram_ integration allows you to send notifications to a _Telegram_ **chat via a bot**.

!!! info "Getting your bot token and chat ID"

    1. Create a new bot by talking to the [**BotFather**](https://t.me/botfather){target="blank"} on _Telegram_.
    2. After creating the bot, you will receive a **bot token**, this will be your `api-token`.
    3. Invite your bot to the chat where you want to receive notifications, or create a new group and add the bot to it.
    4. To get your chat ID, send a message to your desired chat and then visit `https://api.telegram.org/bot<YourApiToken>/getUpdates` in your browser, where `<YourApiToken>` is the token you received from the BotFather. **Look for something like this** in the response, this will be your `chat-id`:

         ```json hl_lines="4"
         {
            // ... other fields ...
            "sender_chat": {
            "id": -343243243111,
            "title": "kuvasz uptime events",
            "type": "channel"
            },
            // ... other fields ...
         }
         ```

### `api-token`

<!-- md:version 2.0.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The **API token of the _Telegram_ bot** that will send the notifications. You can get this token **from the BotFather** when you create your bot.

### `chat-id`

<!-- md:version 2.0.0 -->
<!-- md:flag required -->
<!-- md:type `string` -->

The **chat ID of the _Telegram_ chat** where the notifications will be sent. You can get this ID by following the steps outlined in the **Getting your bot token and chat ID** section.

---

```yaml title="Telegram integration example"
integrations:
  telegram:
    - name: telegram_global
      api-token: 'YourToken'
      chat-id: '-1232642423121'
      global: true
    - name: telegram_disabled
      api-token: 'YourOtherToken'
      chat-id: '-1232546142423121'
      enabled: false
    # ... other Telegram integrations
```

## Do you miss an integration?

If you miss an integration, please [**open an issue**](https://github.com/kuvasz-uptime/kuvasz/issues/new?template=feature_request.md){target="_blank"}, or consider contributing it yourself! We are always open to new integrations and would love to see your contribution.
