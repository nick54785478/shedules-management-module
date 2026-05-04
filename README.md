# 分散式排程任務管理模組

本模組基於 Spring Boot 4.0.6 與 Quartz Scheduler 構建，旨在提供一個高可用、強一致性且具備領域驅動設計（DDD）特性的排程管理系統。

## 核心架構設計

本模組採用 六角架構（Ports and Adapters），確保業務邏輯與技術實現（Quartz）完全解耦：

**Domain Layer**: 
> 包含 ScheduledJob 聚合根與 CronExpression 值物件。

**Application Layer**:
>* ScheduledJobApplicationService 負責協調資料庫持久化與 Quartz 引擎狀態。
>* 透過 CronParserPort 確保進入領域層的數據絕對合法。

**Infrastructure Layer**:
>* QuartzAdapter: 實現 JobSchedulerPort，負責具體的 Quartz 操作。
>* QuartzCronParser: 實現 CronParserPort，利用 Quartz 內建邏輯進行語法檢核。

## 關鍵技術特性

**1. 分散式叢集與高可用 (HA)**
透過資料庫鎖機制實現多節點協作，防止任務重複執行並支援故障轉移（Failover）。
>* 動態識別：各節點啟動時自動生成 instanceId。
>* 心跳監測：每 15 秒向 QRTZ_SCHEDULER_STATE 表更新狀態。

**2. 事務強一致性 (Transactional Integrity)**
本模組採用 共享資料源 (Shared DataSource) 策略：
>* 原子操作：業務資料表（自定義 Job 表）與 Quartz 系統表（QRTZ_）共用同一個 JDBC 連線。
>* 同步回滾：當更新排程（如 pauseTask 或 updateCron）時，若任一環節失敗，兩邊的資料將同步回滾，防止狀態偏差。

**3. 領域保護機制 (The Gatekeeper)**
> 採用 純粹派 DDD 設計，透過 CronParserPort 攔截無效的 Cron 表達式，確保 ScheduledJob 聚合根在記憶體中始終處於合法狀態。

## 開發者指南
